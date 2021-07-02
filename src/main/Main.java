package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;

import model.Comando;
import model.ConexaoBD;
import view.JOptionPaneAvisoErro;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JProgressBar barra = new JProgressBar();
	private int mlSegundos;
	private final JPanel panel = new JPanel();
	private JLabel lblTempoCorrido;
	private final JLabel label = new JLabel("");
	private int seg=0;
	private int min=0;
	private JLabel lbComando, lblNewLabel;


	public Main() {
		setBackground(Color.DARK_GRAY);
		setTitle("Processando seu Pedido....");
		setResizable(false);// seuJFrame
		setType(java.awt.Window.Type.UTILITY);// nao minimizar
		setSize(586, 164);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new MatteBorder(10, 5, 5, 5, (Color) new Color(0, 102, 51)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
			          System.exit(0);
			}
		});

	
		Timer timer = new Timer(1000, new hora());
		timer.start();
		
		mlSegundos=4000;
		barra.setMaximum(mlSegundos);
		barra.setStringPainted(true);
		barra.setBounds(22, 52, 527, 34);
		new Temporizador().start();
		contentPane.add(barra);
		
		lblNewLabel = new JLabel("Criando banco de dados aguarde...");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 13, 523, 40);
		contentPane.add(lblNewLabel);
		panel.setBackground(Color.WHITE);
		panel.setForeground(Color.RED);
		panel.setBounds(22, 108, 527, 15);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblTempoCorrido = new JLabel("Tempo Corrido: ");
		lblTempoCorrido.setForeground(Color.RED);
		lblTempoCorrido.setBounds(170, 0, 77, 14);
		lblTempoCorrido.setFont(new Font("Dialog", Font.BOLD, 10));
		panel.add(lblTempoCorrido);
		
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(245, 0, 50, 14);
		
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setForeground(Color.RED);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(22, 94, 527, 15);
		contentPane.add(panel_1);
		
		JLabel lblComando = new JLabel("Comando: ");
		lblComando.setForeground(Color.RED);
		lblComando.setFont(new Font("Dialog", Font.BOLD, 10));
		lblComando.setBounds(0, 0, 77, 14);
		panel_1.add(lblComando);
		
		lbComando = new JLabel("");
		lbComando.setHorizontalAlignment(SwingConstants.CENTER);
		lbComando.setForeground(Color.BLACK);
		lbComando.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbComando.setBounds(51, 0, 464, 14);
		panel_1.add(lbComando);
		
		
		setVisible(true);
		
	
		
		
	}
	class hora implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			seg++;
			if(seg==60) {
				min++;
				seg=0;
			}
		     label.setText(String.format("%02d:%02d",min,seg));
			
		}
	}
	
	
	public class Temporizador extends Thread{
		public void run() {
			while(barra.getValue()<mlSegundos) {
				try {
					sleep(10);
					barra.setValue(barra.getValue()+10);
					if(barra.getValue()==1000) {
						ConexaoBD bd = new ConexaoBD();
						bd.criarConexao();
						
					}else if(barra.getValue()==2000) {
						
						try {
							lblNewLabel.setText("Atualizando as tabelas do banco aguarde...");
							realizaRestore("C:\\System Clinica\\bkp\\inicio.backup");
						} catch (IOException e) {
							new JOptionPaneAvisoErro(e.getMessage());
							System.exit(0);
						}
					}else if(barra.getValue()==5000) {
						
						lblNewLabel.setText("Configurando acesso remoto...");
						File file = new File("C:\\System Clinica\\pg_hba.conf");
						Comando.copiarUmArquivo(file, "C:\\PostgreSQL\\9.4\\data\\");
					}
					
					if(barra.getValue()==mlSegundos) {
						JOptionPane.showMessageDialog(null, "Banco de dados configurado e atualizado com sucesso !");
						System.exit(0);
					}
				} catch (InterruptedException | SQLException e) {
					  new JOptionPaneAvisoErro("Erro: "+e.getMessage());
					  System.exit(0);
				}
			}
		}
	}
 public  void realizaRestore(String caminho) throws IOException, InterruptedException{      
         
		 final List<String> comandos = new ArrayList<String>();      
     
         comandos.add("C:\\PostgreSQL\\9.4\\bin\\" +"pg_restore.exe"); //testado no windows xp
         comandos.add("-i");      
         comandos.add("-h");      
         comandos.add("localhost");    //ou   comandos.add("192.168.0.1"); 
         comandos.add("-p");      
         comandos.add("5432");      
         comandos.add("-U");      
         comandos.add("postgres");      
         comandos.add("-d");      
         comandos.add("bdclinica");     
         comandos.add("-v");      
         comandos.add(caminho);   // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso.  

         ProcessBuilder pb = new ProcessBuilder(comandos);      
           
         pb.environment().put("PGPASSWORD", "ads54321");     //Somente coloque sua senha         
             final Process process = pb.start();      
       
             final BufferedReader r = new BufferedReader(      
                 new InputStreamReader(process.getErrorStream()));      
             String line = r.readLine();      
             while (line != null) {      
            lbComando.setText(line);      
             line = r.readLine();      
             }      
             r.close();      
             process.waitFor();    
             process.destroy(); 
     }      
	public static void main(String[] args) {
		new Main();
	}
}
