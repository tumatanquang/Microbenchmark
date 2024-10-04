package uc.util;
import java.util.ArrayList;
public class SyncArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -5895597026413583152L;
	public SyncArrayList() {
		super();
	}
	public SyncArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final SyncArrayList<E> shared() {
		return new Shared();
	}
	private final class Shared extends SyncArrayList<E> {
		private static final long serialVersionUID = 7851444873602708266L;
		@Override
		public synchronized boolean add(E value) {
			return super.add(value);
		}
		@Override
		public synchronized void clear() {
			super.clear();
		}
		@Override
		public synchronized boolean contains(Object o) {
			return super.contains(o);
		}
		@Override
		public synchronized E get(int index) {
			return super.get(index);
		}
		@Override
		public synchronized int indexOf(Object o) {
			return super.indexOf(o);
		}
		@Override
		public synchronized boolean isEmpty() {
			return super.isEmpty();
		}
		@Override
		public synchronized E remove(int index) {
			return super.remove(index);
		}
		@Override
		public synchronized boolean remove(Object o) {
			return super.remove(o);
		}
		@Override
		public synchronized int size() {
			return super.size();
		}
	}
}