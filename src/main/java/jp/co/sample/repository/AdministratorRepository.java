package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorテーブルを操作するリポジトリ.
 * 
 * @author oyamadakenji
 *
 */
@Repository
public class AdministratorRepository {
	private static final String tableName = "administrators";
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	// ■insert()
	/**
	 * 管理者情報を挿入する.
	 * 
	 * @param administrator　管理者情報
	 * 
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);

		String insertSql = "INSERT INTO " + tableName
				+ " (name,mail_address,password) VALUES (:name,:mailAddress,:password)";

		template.update(insertSql, param);
	}

	// ■findByMailAddressAndPassword()
	/**
	 * メールアドレスとパスワードから管理者情報を取得する. 存在しない場合はnullを返す.
	 * 
	 * @param mailAddress　メールアドレス
	 * @param password　パスワード
	 * @return 管理者情報　(存在しない場合null)
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
//	パターン2 検索件数が0だったらnullにする。
		String sql = "SELECT id,name,mail_address,password FROM " + tableName
				+ " WHERE mail_address = :mailAddress AND password = :password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);
		
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		if (administratorList.size()==0) {
			return null;
		}
		return administratorList.get(0);
		
//		パターン1 queryForObject() > 例外がある前提 > SQLにバグがある前提
//		try {
//			return template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);			
//		} catch (Exception e) {
//			return null;
//		}

	}
}
