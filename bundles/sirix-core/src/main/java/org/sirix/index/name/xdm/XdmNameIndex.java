package org.sirix.index.name.xdm;

import org.sirix.api.PageWriteTrx;
import org.sirix.index.IndexDef;
import org.sirix.index.avltree.interfaces.References;
import org.sirix.index.name.NameIndex;
import org.sirix.node.interfaces.Record;
import org.sirix.page.UnorderedKeyValuePage;

public interface XdmNameIndex<K extends Comparable<? super K>, V extends References> extends NameIndex<V> {

  XdmNameIndexBuilder createBuilder(PageWriteTrx<Long, Record, UnorderedKeyValuePage> pageWriteTrx, IndexDef indexDef);

  XdmNameIndexListener createListener(PageWriteTrx<Long, Record, UnorderedKeyValuePage> pageWriteTrx,
      IndexDef indexDef);
}
