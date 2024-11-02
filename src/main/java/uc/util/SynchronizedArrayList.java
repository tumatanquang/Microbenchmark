package uc.util;
import java.util.ArrayList;
public class SynchronizedArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -5895597026413583152L;
	public SynchronizedArrayList() {
		super();
	}
	public SynchronizedArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final SynchronizedArrayList<E> shared() {
		return new Shared(this);
	}
	private final class Shared extends SynchronizedArrayList<E> {
		private static final long serialVersionUID = 7851444873602708266L;
		private final SynchronizedArrayList<E> list;
		private Shared(SynchronizedArrayList<E> target) {
			list = target;
		}
		@Override
		public synchronized boolean add(E value) {
			return list.add(value);
		}
		@Override
		public synchronized void clear() {
			list.clear();
		}
		@Override
		public synchronized boolean contains(Object o) {
			return list.contains(o);
		}
		@Override
		public synchronized E get(int index) {
			return list.get(index);
		}
		@Override
		public synchronized int indexOf(Object o) {
			return list.indexOf(o);
		}
		@Override
		public synchronized boolean isEmpty() {
			return list.isEmpty();
		}
		@Override
		public synchronized E remove(int index) {
			return list.remove(index);
		}
		@Override
		public synchronized boolean remove(Object o) {
			return list.remove(o);
		}
		@Override
		public synchronized int size() {
			return list.size();
		}
	}
}