package org.sirix.index.name;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import org.brackit.xquery.atomic.QNm;
import org.sirix.api.PageReadTrx;
import org.sirix.index.Filter;
import org.sirix.index.IndexDef;
import org.sirix.index.IndexFilterAxis;
import org.sirix.index.SearchMode;
import org.sirix.index.avltree.AVLNode;
import org.sirix.index.avltree.AVLTreeReader;
import org.sirix.index.avltree.interfaces.References;
import org.sirix.index.avltree.keyvalue.NodeReferences;
import org.sirix.settings.Fixed;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;

public interface NameIndex<V extends References> {
  default Iterator<NodeReferences> openIndex(PageReadTrx pageRtx, IndexDef indexDef, NameFilter filter) {
    final AVLTreeReader<QNm, NodeReferences> reader =
        AVLTreeReader.getInstance(pageRtx, indexDef.getType(), indexDef.getID());

    if (filter.getIncludes().size() == 1 && filter.getExcludes().isEmpty()) {
      final Optional<NodeReferences> optionalNodeReferences =
          reader.get(filter.getIncludes().iterator().next(), SearchMode.EQUAL);
      return Iterators.forArray(optionalNodeReferences.orElse(new NodeReferences()));
    } else {
      final Iterator<AVLNode<QNm, NodeReferences>> iter =
          reader.new AVLNodeIterator(Fixed.DOCUMENT_NODE_KEY.getStandardProperty());
      final Set<Filter> setFilter = filter == null
          ? ImmutableSet.of()
          : ImmutableSet.of(filter);

      return new IndexFilterAxis<>(iter, setFilter);
    }
  }
}
