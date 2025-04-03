package com.metaverse.mail.service.impl.user;

import com.metaverse.mail.common.JDBCConnection;
import com.metaverse.mail.common.Session;
import com.metaverse.mail.dao.impl.user.UserDaoImpl;
import com.metaverse.mail.dao.interfaces.UserDao;
import com.metaverse.mail.dto.user.UserLoginDto;
import com.metaverse.mail.dto.user.UserProfileDto;
import com.metaverse.mail.dto.user.UserRegisterDto;
import com.metaverse.mail.model.User;
import com.metaverse.mail.service.interfaces.UserService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    // DB 연결 객체 (static으로 선언하여 테스트 클래스 실행 동안 유지됨)
    private static Connection connection;
    private UserService userService;
    private UserDao userDao;

    /*
     * 테스트 시작 전 한 번 실행됨.
     * DB 연결을 설정하여 이후의 테스트에서 사용할 수 있도록 한다.
     */
    @BeforeAll
    static void setUpBeforeClass() throws SQLException {
        System.out.println("사용자 서비스 테스트 시작");
        connection = JDBCConnection.getConnection();
    }

    /*
     * 모든 테스트가 끝난 후 한 번 실행됨.
     * DB 연결을 닫아 자원을 반납한다.
     */
    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("사용자 서비스 테스트 종료 및 자원 반납");
        JDBCConnection.close();
    }

    /*
     * 각 테스트 실행 전에 실행됨.
     * DAO 및 서비스 객체를 초기화한다.
     */
    @BeforeEach
    void setUp() throws SQLException {
        userDao = new UserDaoImpl(connection);
        userService = new UserServiceImpl(userDao);
    }

    /*
     * 회원가입 성공 테스트
     * - 정상적인 정보로 가입하면 true가 반환되어야 한다.
     */
    @Test
    void testRegister_Success() {
        UserRegisterDto userDto = new UserRegisterDto("test98@example.com", "test1234", "전우선");
        boolean result = userService.register(userDto);

        assertTrue(result, "회원가입이 성공해야 합니다.");
    }

    /*
     * 중복된 이메일로 회원가입 시도할 경우 실패해야 한다.
     * - 이미 존재하는 이메일이므로 false가 반환되어야 한다.
     */
    @Test
    void testRegister_DuplicateEmail() {
        UserRegisterDto userDto = new UserRegisterDto("hong@example.com", "hong1234", "홍길동");
        boolean result = userService.register(userDto);

        assertFalse(result, "중복된 이메일은 회원가입이 실패해야 합니다.");
    }

    /*
     * 로그인 성공 테스트
     * - 올바른 이메일과 비밀번호를 입력하면 User 객체가 반환되어야 한다.
     */
    @Test
    void testLogin_Success() {
        UserLoginDto loginDto = new UserLoginDto("park@example.com", "secure4321");
        User user = userService.login(loginDto);

        assertNotNull(user, "로그인 성공 시 User 객체가 반환되어야 합니다.");
        assertEquals("park@example.com", user.getEmailId(), "이메일이 일치해야 합니다.");
    }

    /*
     * 잘못된 비밀번호로 로그인 시도할 경우 실패해야 한다.
     * - 로그인 실패 시 null이 반환되어야 한다.
     */
    @Test
    void testLogin_WrongPassword() {
        UserLoginDto loginDto = new UserLoginDto("hong@example.com", "wrongpass");
        User user = userService.login(loginDto);

        assertNull(user, "잘못된 비밀번호 입력 시 null이 반환되어야 합니다.");
    }

    /*
     * 프로필 수정 성공 테스트
     * - 닉네임을 변경하고 성공 여부를 확인한다.
     */
    @Test
    void testUpdateProfile_Success() {
        User testUser = new User(2, "park@example.com", "secure4321", "박영희", 'A');

        Session.getInstance().login(testUser);

        UserProfileDto profileDto = new UserProfileDto("변경된닉네임", "secure4321", null);
        boolean result = userService.updateProfile(profileDto, 2);

        assertTrue(result, "닉네임 변경이 성공해야 합니다.");
    }

    /*
     * 잘못된 비밀번호로 프로필 수정 시도 시 실패해야 한다.
     * - 비밀번호가 틀릴 경우 false가 반환되어야 한다.
     */
    @Test
    void testUpdateProfile_WrongPassword() {
        UserProfileDto profileDto = new UserProfileDto("닉네임변경실패", "wrongpass", null);
        boolean result = userService.updateProfile(profileDto, 4);

        assertFalse(result, "잘못된 비밀번호로 인해 닉네임 수정이 실패해야 합니다.");
    }

    /*
     * 비밀번호 변경 성공 테스트
     * - 기존 비밀번호가 맞으면 새로운 비밀번호로 변경이 성공해야 한다.
     */
    @Test
    void testChangePassword_Success() {
        boolean result = userService.changePassword("hong1234", "newSecurePass!", 4);

        assertTrue(result, "비밀번호 변경이 성공해야 합니다.");
    }

    /*
     * 잘못된 기존 비밀번호를 입력하면 비밀번호 변경이 실패해야 한다.
     */
    @Test
    void testChangePassword_WrongOldPassword() {
        boolean result = userService.changePassword("wrongpass", "newSecurePass!", 4);

        assertFalse(result, "현재 비밀번호가 일치하지 않으면 실패해야 합니다.");
    }

    /*
     * 계정 비활성화 성공 테스트
     * - 존재하는 사용자의 계정을 비활성화하면 성공해야 한다.
     */
    @Test
    void testDeactivateAccount_Success() {
        boolean result = userService.deactivateAccount(4);

        assertTrue(result, "계정 비활성화가 성공해야 합니다.");
    }

    /*
     * 존재하지 않는 사용자의 계정 비활성화 시도 시 실패해야 한다.
     */
    @Test
    void testDeactivateAccount_NonExistentUser() {
        boolean result = userService.deactivateAccount(9999);

        assertFalse(result, "존재하지 않는 사용자의 비활성화는 실패해야 합니다.");
    }
}