package uc.util;
import javolution.util.FastTable;
public class SynchronizedFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 4288709377943772618L;
	public SynchronizedFastTable() {
		super();
	}
	public SynchronizedFastTable(int capacity) {
		super(capacity);
	}
	@Override
	public final SynchronizedFastTable<E> shared() {
		return new Shared(this);
	}
	private class Shared extends SynchronizedFastTable<E> {
		private static final long serialVersionUID = -6167239388106998276L;
		private final SynchronizedFastTable<E> table;
		private Shared(SynchronizedFastTable<E> target) {
			table = target;
		}
		@Override
		public synchronized boolean add(E value) {
			return table.add(value);
		}
		@Override
		public synchronized void clear() {
			table.clear();
		}
		@Override
		public synchronized boolean contains(Object o) {
			return table.contains(o);
		}
		@Override
		public synchronized E get(int index) {
			return table.get(index);
		}
		@Override
		public synchronized int indexOf(Object o) {
			return table.indexOf(o);
		}
		@Override
		public synchronized boolean isEmpty() {
			return table.isEmpty();
		}
		@Override
		public synchronized E remove(int index) {
			return table.remove(index);
		}
		@Override
		public synchronized boolean remove(Object o) {
			return table.remove(o);
		}
		@Override
		public synchronized int size() {
			return table.size();
		}
	}
}