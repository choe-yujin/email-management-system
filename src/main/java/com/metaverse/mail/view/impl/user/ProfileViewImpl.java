package com.metaverse.mail.view.impl.user;

import com.metaverse.mail.common.ConsoleHelper;
import com.metaverse.mail.dto.user.UserProfileDto;
import com.metaverse.mail.model.User;
import com.metaverse.mail.service.interfaces.UserService;
import com.metaverse.mail.view.interfaces.user.LoginView;
import com.metaverse.mail.view.interfaces.user.ProfileView;
import com.metaverse.mail.common.Session;

public class ProfileViewImpl implements ProfileView {
    private final ConsoleHelper consoleHelper; // ConsoleHelper 객체: 콘솔 입출력 담당
    private final UserService userService; // UserService 객체: 프로필 수정, 비밀번호 변경, 탈퇴 등을 처리
    private final LoginView loginView;  // LoginView 객체 추가

    // ProfileViewImpl 생성자: ConsoleHelper와 UserService 객체를 주입받아 초기화
    public ProfileViewImpl(ConsoleHelper consoleHelper, UserService userService, LoginView loginView) {
        this.consoleHelper = consoleHelper;
        this.userService = userService;
        this.loginView = loginView;
    }

    @Override
    public void showProfileManagement() {
        // 현재 로그인된 사용자의 프로필 정보 가져오기
        UserProfileDto currentProfile = getCurrentUserProfile();

        while (true) {
            consoleHelper.displayHeader("👤 회원 정보 수정");
            System.out.println("1. 닉네임 수정");
            System.out.println("2. 비밀번호 수정");
            System.out.println("3. 회원 탈퇴");
            System.out.println("4. 이전 메뉴로 돌아가기");
            consoleHelper.displayDivider();

            // 사용자로부터 입력 받기
            int choice = consoleHelper.getIntInput("원하는 기능을 선택하세요 (1-4): ", 1, 4);

            // 각 선택지에 맞는 기능 수행
            switch (choice) {
                case 1:
                    // 닉네임 수정 폼 표시
                    showProfileUpdateForm(currentProfile);
                    break;
                case 2:
                    // 비밀번호 수정 폼 표시
                    showPasswordChangeForm(currentProfile);
                    break;
                case 3:
                    // 회원 탈퇴 처리
                    confirmAccountDeletion();
                    break;
                case 4:
                    // 이전 메뉴로 돌아가기
                    return;
            }
        }
    }

    @Override
    public void showProfileInfo(UserProfileDto profile) {
        // 프로필 정보를 화면에 표시하는 로직
        consoleHelper.displayHeader("👤 사용자 프로필");
        System.out.println("닉네임: " + profile.getNickname());
        // 필요한 다른 프로필 정보 추가
        consoleHelper.displayDivider();
    }
    @Override
    public void showProfileUpdateForm(UserProfileDto profile) {
        // 닉네임 수정 폼을 사용자에게 보여주고, 새 닉네임을 입력 받음
        consoleHelper.displayHeader("📝 닉네임 수정");

        String newNickname = consoleHelper.getStringInput("현재 닉네임: " + profile.getNickname() + "\n변경할 닉네임: ");

        // 새 닉네임을 UserProfileDto에 담아서 서비스 메서드를 호출
        UserProfileDto updatedProfile = new UserProfileDto(newNickname, profile.getCurrentPassword(), profile.getNewPassword());

        // 닉네임 수정 요청 처리 (사용자 서비스 호출)
        boolean isUpdated = userService.updateProfile(updatedProfile, Session.getInstance().getCurrentUserId());
        if (isUpdated) {
            System.out.println("→ 닉네임이 성공적으로 변경되었습니다!");
        } else {
            System.out.println("→ 닉네임 변경이 실패했습니다!");
        }

        System.out.println("→ 회원 정보 메뉴로 돌아갑니다.");
        showProfileManagement(); // 수정 후 다시 회원 정보 메뉴로 돌아가기
    }

    @Override
    public void showProfileUpdateResult(boolean success) {
        // 프로필 업데이트 결과를 표시
        if (success) {
            System.out.println("→ 프로필 정보가 성공적으로 수정되었습니다.");
        } else {
            System.out.println("→ 프로필 정보 수정에 실패했습니다.");
        }
    }

    /**
     * 현재 로그인한 사용자의 프로필을 가져오는 메서드.
     * Session 클래스를 통해 로그인된 사용자의 정보를 가져옵니다.
     *
     * @return 현재 로그인한 사용자의 프로필 정보
     */
    private UserProfileDto getCurrentUserProfile() {
        // Session에서 현재 로그인된 사용자의 정보 가져오기
        Session session = Session.getInstance();
        User currentUser = session.getCurrentUser();  // 로그인된 사용자의 User 객체를 가져옴
        String nickname = session.getCurrentUserNickname();  // 로그인된 사용자의 닉네임 가져오기
        String currentPassword = currentUser != null ? currentUser.getEmailPwd() : null;
        String newPassword = "";  // 새 비밀번호는 기본값으로 빈 문자열 설정

        return new UserProfileDto(nickname, currentPassword, newPassword);  // UserProfileDto 반환
    }

    // 비밀번호 변경 폼
    private void showPasswordChangeForm(UserProfileDto profile) {
        // 비밀번호 수정 폼을 사용자에게 보여주고, 현재 비밀번호와 새 비밀번호를 입력 받음
        consoleHelper.displayHeader("🔑 비밀번호 수정");

        String currentPassword = consoleHelper.getStringInput("현재 비밀번호: ");
        String newPassword = consoleHelper.getStringInput("새 비밀번호: ");
        String confirmPassword = consoleHelper.getStringInput("새 비밀번호 확인: ");

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("→ 새 비밀번호가 일치하지 않습니다.");
            return;
        }

        // 비밀번호 변경 처리
        boolean isPasswordChanged = userService.changePassword(currentPassword, newPassword, Session.getInstance().getCurrentUserId());
        if (isPasswordChanged) {
            System.out.println("→ 비밀번호가 성공적으로 변경되었습니다.");
        } else {
            System.out.println("→ 비밀번호 변경 실패.");
        }

        System.out.println("→ 회원 정보 메뉴로 돌아갑니다.");
        showProfileManagement(); // 수정 후 다시 프로필 관리 화면으로 돌아가기
    }

    // 회원 탈퇴 처리
    private void confirmAccountDeletion() {
        consoleHelper.displayHeader("❌ 회원 탈퇴");
        System.out.println("정말로 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.");
        System.out.println("1. 예");
        System.out.println("2. 아니오");
        consoleHelper.displayDivider();
        int choice = consoleHelper.getIntInput("선택 (1-2): ", 1, 2);

        if (choice == 1) {
            boolean isDeactivated = userService.deactivateAccount(Session.getInstance().getCurrentUserId());
            if (isDeactivated) {
                System.out.println("→ 회원 탈퇴가 완료되었습니다.");
                System.out.println("→ 로그인 화면으로 이동합니다.");

                // 로그인 화면으로 돌아가도록 loginView 객체를 호출
                loginView.showLoginForm();  // 인수 없이 호출
            } else {
                System.out.println("→ 회원 탈퇴 실패.");
            }
        } else {
            System.out.println("→ 회원 탈퇴가 취소되었습니다.");
        }
    }
}
