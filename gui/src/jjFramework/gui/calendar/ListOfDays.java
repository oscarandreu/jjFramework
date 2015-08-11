package jjFramework.gui.calendar;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

public class ListOfDays extends ArrayList<DateTime>
{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean contains(Object o) {
		DateTimeComparator	d = DateTimeComparator.getDateOnlyInstance();

		for(int i = 0; i < this.size(); i++)
		{
			if(d.compare(o, this.get(i)) == 0)
			return true;
		}
		return false;
	}
}
