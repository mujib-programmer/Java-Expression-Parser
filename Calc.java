// Applet Kalkulator Sederhana.
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/*
<applet code="Calc" width=200 height=150>
</applet>
*/

public class Calc extends Applet implements ActionListener {
	TextField expText, resText;
	Parser p;
	
	public void init() {
		Label heading = new
		Label("Expression Calculator ", Label.CENTER);
		Label explab = new Label("Expression ", Label.CENTER);
		Label reslab = new Label("Result ", Label.CENTER);
		expText = new TextField(24);
		resText = new TextField(24);
		resText.setEditable(false); // field result hanya untuk menampilkan hasil
		add(heading);
		add(explab);
		add(expText);
		add(reslab);
		add(resText);
		
		/* 
		 * Daftarkan ekspresi pada textfield
		 * untuk menerima event aksi
		 */
		expText.addActionListener(this);
		
		// membuat parser		
		p = new Parser();
		
	} // akhir dari method init
	
	// Pengguna menekan tombol Enter.
	public void actionPerformed(ActionEvent ae) {
		repaint();
	}
	
	public void paint(Graphics g) {
		double result = 0.0;
		String expstr = expText.getText();
		
		try {
			if(expstr.length() != 0)
			result = p.evaluate(expstr);
			
			// untuk membersihkan ekspresi setelah tombol ENTER ditekan
			// gunakan baris berikut
			expText.setText("");
			resText.setText(Double.toString(result));
			showStatus(""); // hapus pesan error sebelumnya
			
		} catch (ParserException exc) {
			showStatus(exc.toString());
			resText.setText("");
			
		}
	} // akhir dari method paint
}
