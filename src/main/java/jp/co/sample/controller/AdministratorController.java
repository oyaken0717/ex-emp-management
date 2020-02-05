package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者関連機能の処理の制御を行うコントローラー.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("")
public class AdministratorController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	AdministratorService administratorService;
	
	/**
	 * 従業員登録する際のリクエストパラメータが格納される.
	 * 
	 * @return リクエスパラメータの入ったオブジェクト
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * ログインする際のリクエストパラメータが格納される.
	 * 
	 * @return リクエスパラメータの入ったログイン画面からのオブジェクト
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	/**
	 * 管理者情報登録画面に遷移する.
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者情報を登録する
	 * @param form　リクエストパラメータ
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		
//■BeanUtilsはなぜ使えないの？
//		使えます。
//		formの内容=htmlの*{}の内容だから、テーブルのカラムの情報とは無関係。
		BeanUtils.copyProperties(form, administrator);
//		administrator.setName(form.getName());
//		administrator.setMailAddress(form.getMailAddress());
//		administrator.setPassword(form.getPassword());
		
		administratorService.insert(administrator);
		return "/";
	}
	
	/**
	 * ログイン画面に遷移する.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	@RequestMapping("/login")
	public String login(LoginForm form,Model model) {
		System.out.println(form);
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator==null) {
			model.addAttribute("error", "メールアドレスまたはパスワードが不正です");
			return toLogin();
//			①html
//			return "forward:/";
//			return "forward:/exam1/";
		}
		
		session.setAttribute("administratorName", administrator);
		return "forward:/eployee/showList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
