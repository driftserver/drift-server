package io.drift.ui.infra;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.resource.PackageResourceReference;

public class WicketUtil {

	public interface SerializableConsumer<T> extends Consumer<T>, Serializable {
	}

	public interface SerializableBiConsumer<T, U> extends BiConsumer<T, U>, Serializable {
	}

	public static void addClass(Component component, String className) {
		component.add(new AttributeAppender("class", className, " "));
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

	static public <T> List<Integer> indices(List<T> list) {
		return IntStream.range(0, list.size()).boxed().collect(Collectors.toList());
	}



}
