package uc.util;
import javolution.util.FastTable;
public class SyncFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 4288709377943772618L;
	public SyncFastTable() {
		super();
	}
	public SyncFastTable(int capacity) {
		super(capacity);
	}
	public boolean addElement(E value) {
		return super.add(value);
	}
	public void clearAll() {
		super.clear();
	}
	public boolean contain(Object o) {
		return super.contains(o);
	}
	public E getElement(int index) {
		return super.get(index);
	}
	public int getIndexOf(Object o) {
		return super.indexOf(o);
	}
	public boolean empty() {
		return super.isEmpty();
	}
	public E removeElement(int index) {
		return super.remove(index);
	}
	public int getSize() {
		return super.size();
	}
	@Override
	public final SyncFastTable<E> shared() {
		return new Shared();
	}
	private class Shared extends SyncFastTable<E> {
		private static final long serialVersionUID = -6167239388106998276L;
		@Override
		public synchronized boolean addElement(E value) {
			return super.add(value);
		}
		@Override
		public synchronized void clearAll() {
			super.clear();
		}
		@Override
		public synchronized boolean contain(Object o) {
			return super.contains(o);
		}
		@Override
		public synchronized E getElement(int index) {
			return super.get(index);
		}
		@Override
		public synchronized int getIndexOf(Object o) {
			return super.indexOf(o);
		}
		@Override
		public synchronized boolean empty() {
			return super.isEmpty();
		}
		@Override
		public synchronized E removeElement(int index) {
			return super.remove(index);
		}
		@Override
		public synchronized boolean remove(Object o) {
			return super.remove(o);
		}
		@Override
		public synchronized int getSize() {
			return super.size();
		}
	}
}