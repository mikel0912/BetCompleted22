package TableAdapter;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.ApustuAnitza;
import domain.Apustua;
import domain.Registered;

public class UserAdapter extends AbstractTableModel{
	private String[] colNames = {"Event", "Question", "Event date", "Bet(€)"};
	private ArrayList<Apustua> apAll;
	private Registered r;
	public UserAdapter(Registered r) {
		this.r=r;
		apAll = new ArrayList<Apustua>();
		for(ApustuAnitza a: r.getApustuAnitzak()) {
			apAll.addAll(a.getApustuak());
			
		}
	}

	@Override
	public int getRowCount() {
		return apAll.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		if(columnIndex==0) {
			temp = apAll.get(rowIndex).getKuota().getQuestion().getEvent();
		}else if(columnIndex ==1) {
			temp = apAll.get(rowIndex).getKuota().getQuestion();
		}else if(columnIndex ==2) {
			temp = apAll.get(rowIndex).getKuota().getQuestion().getEvent().getEventDate();
		}else if(columnIndex ==3) {
			temp = apAll.get(rowIndex).getApustuAnitza().getBalioa();
		}
		return temp;
	}
	
	public String getColumnName(int columnIndex) {
		return colNames[columnIndex];
	}

}
