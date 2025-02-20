package uc.util;
import javolution.util.FastTable;
public class SynchronizedThisMutexFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 4288709377943772618L;
	public SynchronizedThisMutexFastTable() {
		super();
	}
	public SynchronizedThisMutexFastTable(int capacity) {
		super(capacity);
	}
	@Override
	public final SynchronizedThisMutexFastTable<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends SynchronizedThisMutexFastTable<E> {
		private static final long serialVersionUID = -6167239388106998276L;
		private final SynchronizedThisMutexFastTable<E> table;
		private final Object mutex;
		private Shared(SynchronizedThisMutexFastTable<E> target) {
			table = target;
			mutex = this;
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