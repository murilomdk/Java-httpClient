//teste_git 

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.awt.Color;

public class Cliente {

	private JFrame frame;
	private JTextField tbusuario;
	private JTextField tbsenha;
	private JTextField tbservidor;
	private JTextField tbStatus;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente window = new Cliente();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Cliente() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 496, 326);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblUsuario = new JLabel("Senha");
		lblUsuario.setBounds(28, 145, 46, 14);
		frame.getContentPane().add(lblUsuario);

		JLabel label = new JLabel("Usuario");
		label.setBounds(18, 114, 46, 14);
		frame.getContentPane().add(label);

		tbusuario = new JTextField();
		tbusuario.setColumns(10);
		tbusuario.setBounds(79, 111, 113, 20);
		frame.getContentPane().add(tbusuario);

		JLabel lblClienteConexo = new JLabel("Cliente - Conex\u00E3o HTTP");
		lblClienteConexo.setFont(new Font("Ebrima", Font.BOLD, 15));
		lblClienteConexo.setBounds(43, 11, 307, 23);
		frame.getContentPane().add(lblClienteConexo);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(216, 72, 46, 14);
		frame.getContentPane().add(lblStatus);

		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() { // 1
			public void actionPerformed(ActionEvent arg0) { // 2

				String usuario = tbusuario.getText();
				String senha = tbsenha.getText();
				String servidor = tbservidor.getText();

				CredentialsProvider credenciais = new BasicCredentialsProvider();
				credenciais.setCredentials(new AuthScope(servidor, 80),
						new UsernamePasswordCredentials(usuario, senha));
				CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credenciais)
						.build();

				try {
					HttpGet httpget = new HttpGet("http://" + servidor);

					System.out.println("Executando solicitação: " + httpget.getRequestLine());
					CloseableHttpResponse response = httpclient.execute(httpget);
					try {
						System.out.println("----------------------------------------");
						System.out.println("response.getStatusLine(): ");
						System.out.println(response.getStatusLine());

						if (response.getStatusLine().getStatusCode() == 200) {
							tbStatus.setText("200 - Acesso permitido!");
						}

						else if (response.getStatusLine().getStatusCode() == 401) {

							tbStatus.setText("401 - Acesso Negado!");
						}

					} finally {
						response.close();
					}

				} catch (ConnectException e2) {

					e2.printStackTrace();
					// tbStatus.setText(e2.getMessage());
					tbStatus.setText("Tempo de Conexão Excedido");

				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				} finally {
					
					try {
						
						httpclient.close();
						
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			}// 2
		} // 1
		); // fim ()

		btnConectar.setBounds(54, 188, 89, 23);
		frame.getContentPane().add(btnConectar);
		tbsenha = new JTextField();
		tbsenha.setBounds(79, 142, 113, 20);
		frame.getContentPane().add(tbsenha);
		tbsenha.setColumns(10);
		tbservidor = new JTextField();
		tbservidor.setBounds(79, 69, 113, 20);
		frame.getContentPane().add(tbservidor);
		tbservidor.setColumns(10);
		JLabel lblServidor = new JLabel("Servidor");
		lblServidor.setBounds(18, 72, 46, 14);
		frame.getContentPane().add(lblServidor);
		tbStatus = new JTextField();
		tbStatus.setBounds(272, 69, 174, 20);
		frame.getContentPane().add(tbStatus);
		tbStatus.setColumns(10);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSair.setBounds(363, 188, 69, 23);
		frame.getContentPane().add(btnSair);
	}
}
