package jjFramework.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**Clase encargada de la configuración de un formulario en particular*/
public class FormConfig implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	/**Identificador del formulario para el cual queremos cargar la configuración. */
	private UUID formId;
	
	/**Grids que tiene el formulario*/
	private List<GridConfig> grids = new ArrayList<GridConfig>();
	
	public List<GridConfig> getGrids() {
		return grids;
	}
	public void setGrids(List<GridConfig> grids) {
		this.grids = grids;
	}
	
	public UUID getFormId() {
		return formId;
	}
	public void setFormId(UUID formId) {
		this.formId = formId;
	}

	
	public FormConfig(){}

	public void addGrid(GridConfig grid)
	{
		grids.add(grid);
	}
}
