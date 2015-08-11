package jjFramework.gui.components;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class pnlBaseView extends JPanel {

	private	JHeaderPanel pnlHeader = new JHeaderPanel();
	
	/**
	 * Create the panel.
	 */
	public pnlBaseView(String titulo) {
		pnlHeader.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		pnlHeader.setTitulo(titulo);

		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 10, 0, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
	
		GridBagConstraints gbc_pnlHeader = new GridBagConstraints();
		gbc_pnlHeader.gridwidth = 13;
		gbc_pnlHeader.insets = new Insets(0, 0, 5, 5);
		gbc_pnlHeader.fill = GridBagConstraints.BOTH;
		gbc_pnlHeader.gridx = 1;
		gbc_pnlHeader.gridy = 1;
		add(pnlHeader, gbc_pnlHeader);

	}

	public void setImagenTitulo(ImageIcon imagen)
	{
		pnlHeader.setImagenTitulo(imagen);
	}
	
protected void LoadData() {
		
		System.out.print("frmBase");
	}
	
	protected void DataBindings() {}
	
	
	public void OpenForm()
	{
		
		LoadData();
		
		DataBindings();
		
		this.setVisible(true);
	}

}
