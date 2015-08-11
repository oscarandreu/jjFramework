package jjFramework.gui.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * The Class GenericTableModel.
 */
public abstract class GenericTableModel< T > extends AbstractTableModel {

	/** The serialVersionUID. */
	private static final long serialVersionUID = 2953355508541913548L;

	/** The data. */
	protected List< T > data = new ArrayList< T >();
	protected Class<?> dataClass;

	private List<Field> fields = new ArrayList<Field>();

	/**
	 * Instantiates a new generic table model.
	 */
	public GenericTableModel() {
		super();
	}

	/**
	 * Instantiates a new generic table model.
	 *
	 * @param list The list with the data to set.
	 */
	public GenericTableModel( final List< T > list ) {
		super();
		data.addAll( list );
		dataClass = list.get(0).getClass();

		for (Field f : dataClass.getDeclaredFields()) {
			fields.add(f);
		}
	}

	/**
	 * Adds the row.
	 *
	 * @param row the row to add.
	 */
	public void add( final T row ) {
		data.add( row );
		fireTableDataChanged();
	}

	/**
	 * Adds the list.
	 *
	 * @param list the list of rows to add.
	 */
	public void addList( final List< T > list ) {
		data.addAll( list );
		fireTableDataChanged();
	}

	/**
	 * Removes all data from this table model.
	 */
	public void clear() {
		if(null != data && !data.isEmpty()){
			data.clear();
			fireTableDataChanged();
		}
	}

	/**
	 * Gets the row from the given index.
	 *
	 * @param row The index from the row to get.
	 *
	 * @return the row from the given index.
	 */
	public T get( final int row ) {
		return data.get( row );
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List< T > getData() {
		return data;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return the row count
	 *
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Removes the given Object.
	 *
	 * @param row the row
	 *
	 * @return the removed Object or null if the object is not in the
	 * tablemodel.
	 */
	public T remove( final T row ) {

		final int index = data.indexOf( row );
		if ( index != -1 ) {
			try {
				return data.remove( index );
			} finally {
				fireTableDataChanged();
			}
		}
		return null;
	}

	/**
	 * Removes the all.
	 *
	 * @param selectedRows the selected rows
	 *
	 * @return the list< t>
	 */
	public List< T > removeAll( final int [] selectedRows ) {
		final List< T > removedList = new ArrayList< T >();
		final int lastIndex = selectedRows.length - 1;
		for ( int i = lastIndex; -1 < i; i-- ) 
		{
			final int selectedRow = selectedRows[ i ];
			final T row = removeAt( selectedRow );
			removedList.add( row );
		}
		fireTableDataChanged();
		return removedList;
	}

	/**
	 * Removes the all the given Object.
	 *
	 * @param toRemove the to remove
	 *
	 * @return the list< t> the removed Objects.
	 */
	public List< T > removeAll( final List< T > toRemove ) {
		final List< T > removedList = new ArrayList< T >();
		for ( final T t: toRemove ) {
			final int index = data.indexOf( t );
			if ( index != -1 ) {
				removedList.add( data.remove( index ) );
			}
		}
		fireTableDataChanged();
		return removedList;
	}

	/**
	 * Removes the row at the given index.
	 *
	 * @param row The index from the row to remove.
	 *
	 * @return the removed row at the given index.
	 */
	public T removeAt( final int row ) {
		try {
			return data.remove( row );
		} finally {
			fireTableDataChanged();
		}
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData( final List< T > data ) {
		this.data = data;
	}

	/**
	 * Update the row.
	 *
	 * @param row the row
	 */
	public void update( final T row ) {
		final int index = data.indexOf( row );
		if ( index != -1 ) {
			data.set( index, row );
			fireTableDataChanged();
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object retvalue = null;	
		T row = get(rowIndex);
		Field f = fields.get(columnIndex);

		try {
			f.setAccessible(true);
			retvalue =f.get(row);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retvalue;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return fields.get(columnIndex).getName();
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> c =fields.get(columnIndex).getDeclaringClass();

		if(! c.isPrimitive())
			c = String.class;

		return  c.getClass();
	}
	
	@Override
	public int getColumnCount() {
		return fields.size();
	}

}