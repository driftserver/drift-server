package io.drift.ui.infra;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

public class WicketUtil {

	public interface SerializableConsumer<T> extends Consumer<T>, Serializable {
	}

	public static AjaxFallbackLink<Void> ajaxLink(String wicketId, SerializableConsumer<AjaxRequestTarget> lambda) {
		AjaxFallbackLink<Void> link = new AjaxFallbackLink<Void>(wicketId) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				lambda.accept(target);
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
		return new ListView<T>(wicketId, items) {
			@Override
			protected void populateItem(final ListItem<T> item) {
				lambda.accept(item);
			}
		};
	}

}
