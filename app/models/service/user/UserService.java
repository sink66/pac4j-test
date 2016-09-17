package models.service.user;

import java.util.Optional;

import org.pac4j.core.profile.CommonProfile;

import models.entity.User;

import javax.inject.Inject;

public class UserService {

	@Inject
	UserModelService userModelService;

	public static UserService use() {
		return new UserService();
	}

	/**
	 * SNS経由の登録orログイン処理
	 *
	 * @param profile
	 * @return
	 */
	public Optional<User> createOrLogin(CommonProfile profile) {

		// DBに同じSNSIDのユーザーが存在したら登録せずログイン
		Optional<User> userOps = userModelService.findBySnsId(profile.getId());
		if(userOps.isPresent()){
			return userOps;
		}

		// 新規作成処理
		User user = new User();
		user.sns_id = profile.getId();
		user.name = profile.getDisplayName();

		return userModelService.save(user);
	}

	public boolean delete(Long userId) {
		Optional<User> userOps = userModelService.findById(userId);
		if (userOps.isPresent()) {
			return userModelService.delete(userOps.get());
		}
		return false;
	}

}
