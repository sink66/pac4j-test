package models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {

	/* SNSが提供する一意なID */
	@Column(unique = true)
	public String sns_id;

	/* ユーザーネーム */
	public String name;

	/* メールアドレス */
	public String mail;

	/* パスワード */
	public String password;

	public static Finder<Long, User> finder = new Finder<Long, User>(User.class);

}
