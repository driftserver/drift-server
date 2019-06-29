package io.drift.ui.infra;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.danekja.java.util.function.serializable.SerializableSupplier;

public class WicketUtil {

	static public AjaxEventBehavior arrowkeyBehavior(SerializableBiConsumer<String, AjaxRequestTarget> lambda) {

		return new AjaxEventBehavior("keydown"){
			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);

				IAjaxCallListener listener = new AjaxCallListener(){
					@Override
					public CharSequence getPrecondition(Component component) {
						return  "var keycode = Wicket.Event.keyCode(attrs.event);" +
								"var result = ((keycode >= 37) && (keycode <= 40));" +  // arrow keys
								"if (result) attrs.event.preventDefault();" +
								"return result;";
					}
				};
				attributes.getAjaxCallListeners().add(listener);

				//Append the pressed keycode to the ajaxrequest
				attributes.getDynamicExtraParameters()
						.add("var eventKeycode = Wicket.Event.keyCode(attrs.event);" +
								"return {keycode: eventKeycode};");

				//whithout setting, no keyboard events will reach any inputfield
				// attributes.setPreventDefault(true);
			}

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				//Extract the keycode parameter from RequestCycle
				final Request request = RequestCycle.get().getRequest();
				final String jsKeycode = request.getRequestParameters()
						.getParameterValue("keycode").toString("");

				lambda.accept(jsKeycode, target);
			}
		};
	}

	static public String CUSTOM_EVENT_NAME = "UnloadDetectedCustomEvent";

	static public WebMarkupContainer unloadTrackingBehaviour(String id, SerializableConsumer<AjaxRequestTarget> lambda) {

		return (WebMarkupContainer) new WebMarkupContainer(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(IHeaderResponse response) {
				super.renderHead(response);
				StringWriter sw = new StringWriter();
				sw.append("console.log('binding onbeforeunload handler') ; $(window).bind('beforeunload', function() { $('#");
				sw.append(getMarkupId());
				sw.append("').trigger('");
				sw.append(CUSTOM_EVENT_NAME);
				sw.append("'); });");
				response.render(OnDomReadyHeaderItem.forScript(sw.toString()));
			}

		}
		.setOutputMarkupId(true)
		.add(new AjaxEventBehavior(CUSTOM_EVENT_NAME) {
			private static final long serialVersionUID = 1L;
			protected void onEvent(final AjaxRequestTarget target) { lambda.accept(target); }
		});

	}

	public interface SerializableConsumer<T> extends Consumer<T>, Serializable {
	}

	public interface SerializableBiConsumer<T, U> extends BiConsumer<T, U>, Serializable {
	}

	public static void addClass(Component component, String className) {
		component.add(new AttributeAppender("class", className, " "));
	}

	public static void addClasses(Component component, String... classNames) {
		Arrays.stream(classNames).forEach(className -> addClass(component, className));
	}

	public static void addClasses(Component component, SerializableSupplier<String> supplier) {
		component.add(new CssClassNameModifier(LambdaModel.of(supplier)));
	}

	public static void addCssResource(String fileName, Class<?> location, IHeaderResponse response) {
		PackageResourceReference ref = new PackageResourceReference(location, fileName);
		CssHeaderItem cssHeaderItem = CssHeaderItem.forReference(ref);
		response.render(cssHeaderItem);
	}

	public static Label label(String id, Serializable text) {
		return new Label(id, text);
	}

	public static Label label(String id) {
		return new Label(id);
	}

	public static AjaxFallbackLink<Void> ajaxLink(String wicketId, SerializableConsumer<AjaxRequestTarget> lambda) {
		AjaxFallbackLink<Void> link = new AjaxFallbackLink<Void>(wicketId) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(Optional<AjaxRequestTarget> target) {
				lambda.accept(target.get());
			}
		};
		return link;
	}

	public static ExternalLink externalLink(String id, String url) {
		return new ExternalLink(id, url);
	}

	public static <PARAM> AjaxFallbackLink<Void> ajaxLink(String wicketId, SerializableBiConsumer<AjaxRequestTarget, PARAM> lambda, PARAM param) {
		AjaxFallbackLink<Void> link = new AjaxFallbackLink<Void>(wicketId) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(Optional<AjaxRequestTarget> target) {
				lambda.accept(target.get(), param);
			}
		};
		return link;
	}

	static public <T> DataView<T> dataView(String wicketId, IDataProvider<T> items, Consumer<ListItem<T>> lambda) {
		return new DataView<T>(wicketId, items) {

			@Override
			protected void populateItem(Item<T> item) {
				lambda.accept(item);

			}
		};
	}

	static public <T> ListView<T> listView(String wicketId, List<T> items, SerializableConsumer<ListItem<T>> lambda) {
		ListView<T> listView  = new ListView<T>(wicketId, items) {
			@Override
			protected void populateItem(final ListItem<T> item) {
				lambda.accept(item);
			}

		};
		return listView;
	}

	static public <T> ListView<T> listView(String wicketId, IModel<List<T>> items, SerializableConsumer<ListItem<T>> lambda) {
		ListView<T> listView  = new ListView<T>(wicketId, items) {
			@Override
			protected void populateItem(final ListItem<T> item) {
				lambda.accept(item);
			}

		};
		return listView;
	}

	static public <T> List<Integer> indices(List<T> list) {
		return IntStream.range(0, list.size()).boxed().collect(Collectors.toList());
	}

	static public WebMarkupContainer div(String id) {
		return new WebMarkupContainer(id);
	}



}
