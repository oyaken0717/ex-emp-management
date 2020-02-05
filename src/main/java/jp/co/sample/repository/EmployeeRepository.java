package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author oyamadakenji
 *
 */
@Repository
public class EmployeeRepository {
	private static final String tableName = "employees";

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hireDate"));

		employee.setMailAddress(rs.getString("mailAddress"));
		employee.setZipCode(rs.getString("zipCode"));
		employee.setAdderss(rs.getString("adderss"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));

		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependentsCount"));

		return employee;
	};

	/**
	 * 従業員一覧情報を入社日順で取得する.<br>
	 * 従業員が存在しない場合はサイズ0件の従業員一覧を返す
	 * @return 情報の入ったemployeesテーブルの情報
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM "
				+ tableName + " ORDER BY hire_date";
		List<Employee> employeelist = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeelist;
	}

	/**
	 * 主キーから従業員情報を取得する.
	 *
	 * @param id
	 * @return 情報が詰まった1行の情報
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM "
				+ tableName + " WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}

	/**
	 * 従業員情報を変更する.
	 * 今回は従業員情報の扶養人数だけを更新する
	 * @param employee
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String updateSql = "UPDATE employees SET dependents_count = :dependentCount WHERE id = :id";
		template.update(updateSql, param);
	}

}
