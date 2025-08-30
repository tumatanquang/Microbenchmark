package uc.util;
import java.util.ArrayList;
public class SynchronizedThisArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -5895597026413583152L;
	public SynchronizedThisArrayList() {
		super();
	}
	public SynchronizedThisArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final SynchronizedThisArrayList<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends SynchronizedThisArrayList<E> {
		private static final long serialVersionUID = 7851444873602708266L;
		private final SynchronizedThisArrayList<E> list;
		private final Object mutex;
		private Shared(SynchronizedThisArrayList<E> target) {
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