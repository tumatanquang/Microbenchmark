package uc.util;
import java.util.ArrayList;
public class SynchronizedThisMutexArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -5895597026413583152L;
	public SynchronizedThisMutexArrayList() {
		super();
	}
	public SynchronizedThisMutexArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final SynchronizedThisMutexArrayList<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends SynchronizedThisMutexArrayList<E> {
		private static final long serialVersionUID = 7851444873602708266L;
		private final SynchronizedThisMutexArrayList<E> list;
		private final Object mutex;
		private Shared(SynchronizedThisMutexArrayList<E> target) {
			list = target;
			mutex = this;
		}
		@Override
		public boolean add(E value) {
			synchronized(mutex) {
				return list.add(value);
			}
		}
		@Override
		public void clear() {
			synchronized(mutex) {
				list.clear();
			}
		}
		@Override
		public boolean contains(Object o) {
			synchronized(mutex) {
				return list.contains(o);
			}
		}
		@Override
		public E get(int index) {
			synchronized(mutex) {
				return list.get(index);
			}
		}
		@Override
		public int indexOf(Object o) {
			synchronized(mutex) {
				return list.indexOf(o);
			}
		}
		@Override
		public boolean isEmpty() {
			synchronized(mutex) {
				return list.isEmpty();
			}
		}
		@Override
		public E remove(int index) {
			synchronized(mutex) {
				return list.remove(index);
			}
		}
		@Override
		public boolean remove(Object o) {
			synchronized(mutex) {
				return list.remove(o);
			}
		}
		@Override
		public int size() {
			synchronized(mutex) {
				return list.size();
			}
		}
	}
}