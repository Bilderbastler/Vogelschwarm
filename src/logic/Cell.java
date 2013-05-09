package logic;

import java.util.ArrayList;
/**
 * Die Welt wird in Felder aufgeteilt, um den Inhalt der Welt besser handhaben zu können.
 * Diese Klasse repräsentiert ein Feld der Welt. Sie enthält referenzen zu den Objekten, 
 * die sich in ihr gerade befinden und zu allen umliegenden Zellen. 
 * @author florianneumeister
 *
 * @param <T> Von welcher Art müssen Objekte sein, damit sie als Inhalt dieses Feldes erfasst werden können
 */
public class Cell<T> {
	/** der Inhalt der Zelle */
	private ArrayList<T> content;
	/** alle benachbarten Zellen */
	private ArrayList<Cell<T>> links;
	
	/**
	 * Erzeugt eine neue Zelle. 
	 */
	public Cell() {
		this.content = new ArrayList<T>();
		this.links = new ArrayList<Cell<T>>(8);
	}
	
	/**
	 * Melde eine Zelle als Nachbar an
	 * @param c die neue Nachbarzelle
	 */
	public void addLink(Cell<T> c){
		this.links.add(c);
	}
	
	/**
	 * Hole den Inhalt dieser Zelle
	 * @return der Inhalt
	 */
	public ArrayList<T> getContent(){
		return this.content;
	}
	
	/**
	 * füge Element dem Zelleninhalt hinzu
	 * @param content
	 */
	public void addContent(T content){
		this.content.add(content);
	}
	/**
	 * leere die Zelle
	 */
	public void removeContent(){
		this.content.clear();
	}
	/**
	 * Hole den Inhalt dieser und aller benachbarten Zellen
	 * 
	 * @return der gesammte Inhalt der Welt in dieser Gegend
	 */
	public ArrayList<T> getNeighborContent(){
		ArrayList<T> cont = new ArrayList<T>();
		cont.addAll(content);
		for (Cell<T> t : links) {
			cont.addAll(t.getContent());
		}
		return cont;
	}
	
	/**
	 * Erzeugt ein zweidimensionales Array von Zellen, in dem jede Zelle seine unmittelbaren Nachbarn kennt.
	 * 
	 * @return das fertig mit Zellen gefüllte Array
	 */
	@SuppressWarnings("unchecked")
	public Cell<T>[][] createCellGrid(int X, int Y){
		Cell<T>[][] cells =  new Cell[X][Y];
		// Lege alle Zellen an
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell<T>();
			}
		}
		
		//erzeuge die Verlinkung zu den Nachbarn jeder Zelle
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				for (int k = i-1; k <= i+1; k++) {
					for (int l = j-1; l <= j+1; l++) {
						if((i == k && j == l) || l < 0 || k < 0 || k >= cells.length -1|| l >= cells[i].length -1){
							continue;
						}
						cells[i][j].addLink(cells[k][l]);
					}
				}
			}
		}
		return cells;
	}
}
