package aplicacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Programa {

	public static void main(String[] args) {
		
		Connection conexao = null;
		Statement st = null;
		
		try {
			conexao = DB.getConnection();
			
			conexao.setAutoCommit(false);
			
			st = conexao.createStatement();
			
			int linhasAfetadas1 = st.executeUpdate("UPDATE vendedor SET SalarioBase = 2090 WHERE IdDepartamento = 1");
			
/*			int x = 1;
			
			if (x < 2) {
				throw new SQLException("Simulação de erro");
			}
*/			
			int linhasAfetadas2 = st.executeUpdate("UPDATE vendedor SET SalarioBase = 3090 WHERE IdDepartamento = 2");
			
			conexao.commit();
			
			System.out.println("Linhas Afetadas 1 : " + linhasAfetadas1);
			System.out.println("Linhas Afetadas 2 : " + linhasAfetadas2);
					
		}
		catch(SQLException e) {
			try {
				conexao.rollback();
				throw new DbException("Transação não foi concluída, rollback executado. Causado por: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DbException("Erro ao realizar o rollback. Causado por: " + e1.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}		
	}
}
