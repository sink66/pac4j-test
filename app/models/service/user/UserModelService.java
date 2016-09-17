package models.service.user;

import java.util.Optional;

import models.entity.User;
import models.service.ModelService;

public class UserModelService implements ModelService<User> {

	public static UserModelService use() {
		return new UserModelService();
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(id).map(userId -> User.finder.byId(userId));
	}

	public Optional<User> findBySnsId(String snsId) {
		return Optional.ofNullable(snsId).map(sns_id -> User.finder.where().eq("sns_id", sns_id).findUnique());
	}

	@Override
	public Optional<User> save(User entry) {
		Optional<User> entryOps = Optional.ofNullable(entry);
		if (entryOps.isPresent()) {
			entry.save();
			return Optional.ofNullable(entry);
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> update(User entry) {
		Optional<User> entryOps = Optional.ofNullable(entry);
		if (entryOps.isPresent()) {
			entry.update();
			return Optional.ofNullable(entry);
		}
		return Optional.empty();
	}

	@Override
	public boolean delete(User entry) {
		Optional<User> entryOps = Optional.ofNullable(entry);
		if (entryOps.isPresent()) {
			entryOps.get().delete();
			return true;
		}
		return false;
	}

}
