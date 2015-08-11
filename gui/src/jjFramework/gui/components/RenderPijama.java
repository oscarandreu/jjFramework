package jjFramework.gui.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class RenderPijama extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
	      super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
	      if ( row%2 != 0)
	      {
	         this.setOpaque(true);
	         this.setBackground(new Color(229, 236,249));
	      } else
	      {
	    	  this.setBackground(Color.white);
	      }
	      return this;
	   }
	
	
	}

