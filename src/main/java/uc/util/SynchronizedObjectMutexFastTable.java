package uc.util;
import javolution.util.FastTable;
public class SynchronizedObjectMutexFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 4288709377943772618L;
	public SynchronizedObjectMutexFastTable() {
		super();
	}
	public SynchronizedObjectMutexFastTable(int capacity) {
		super(capacity);
	}
	@Override
	public final SynchronizedObjectMutexFastTable<E> shared() {
		return new Shared(this);
	}
	private class Shared extends SynchronizedObjectMutexFastTable<E> {
		private static final long serialVersionUID = -6167239388106998276L;
		private final SynchronizedObjectMutexFastTable<E> table;
		private final Object mutex;
		private Shared(SynchronizedObjectMutexFastTable<E> target) {
			table = target;
			mutex = new Object();
		}
		@Override
		public boolean add(E value) {
			synchronized(mutex) {
			return table.add(value);
			}
		}
		@Override
		public void clear() {
			synchronized(mutex) {
			table.clear();
			}
		}
		@Override
		public boolean contains(Object o) {
			synchronized(mutex) {
			return table.contains(o);
			}
		}
		@Override
		public E get(int index) {
			synchronized(mutex) {
			return table.get(index);
			}
		}
		@Override
		public int indexOf(Object o) {
			synchronized(mutex) {
			return table.indexOf(o);
			}
		}
		@Override
		public boolean isEmpty() {
			synchronized(mutex) {
			return table.isEmpty();
			}
		}
		@Override
		public E remove(int index) {
			synchronized(mutex) {
			return table.remove(index);
			}
		}
		@Override
		public boolean remove(Object o) {
			synchronized(mutex) {
			return table.remove(o);
			}
		}
		@Override
		public int size() {
			synchronized(mutex) {
			return table.size();
			}
		}
	}
}