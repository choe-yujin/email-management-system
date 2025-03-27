package com.metaverse.mail.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * XML 파일에서 SQL 쿼리를 로드하고 관리하는 유틸리티 클래스
 * 
 * 이 클래스는 SQL 쿼리를 코드에서 분리하여 외부 XML 파일(queries.xml)에서 관리합니다.
 * 애플리케이션 시작 시 모든 쿼리를 로드하여 메모리에 캐싱하며, 필요할 때 ID를 통해 접근할 수 있습니다.
 * 
 * 이와 같은 방식의 장점:
 *   SQL 쿼리와 Java 코드 분리로 유지보수성 향상
 *   쿼리 변경 시 코드 재컴파일 불필요
 *   쿼리 재사용 용이
 *   중앙 집중식 쿼리 관리
 * 
 * @author 유진
 * @version 1.0
 */
public class QueryUtil {
    /** 쿼리 ID와 SQL 문자열을 매핑하는 HashMap */
    private static final Map<String, String> queries = new HashMap<>();

    /**
     * 정적 초기화 블록
     * 
     * 클래스가 로드될 때 한 번만 실행되어 XML 파일에서 쿼리를 로드합니다.
     */
    static {
        loadQueries();
    }

    /**
     * XML 파일에서 SQL 쿼리를 로드하여 메모리(HashMap)에 저장합니다.
     * 
     * 이 메서드는 다음 단계로 실행됩니다:
     *   리소스 폴더에서 queries.xml 파일을 InputStream으로 로드
     *   XML 파싱 및 Document 객체 생성
     *   모든 'query' 태그 요소 검색
     *   각 query 요소에서 id 속성과 SQL 텍스트 추출
     *   추출한 정보를 HashMap에 저장
     * 
     * 오류 발생 시 RuntimeException으로 예외를 감싸서 던집니다.
     * 
     * @throws RuntimeException XML 파일을 찾을 수 없거나 파싱 중 오류 발생 시
     */
    private static void loadQueries() {
        try {
            // 클래스 로더를 통해 "queries.xml" 파일을 InputStream으로 가져옴
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("queries.xml");

            // InputStream이 null인 경우, 즉 파일을 찾지 못한 경우 예외 발생
            if (inputStream == null) {
                throw new RuntimeException("queries.xml 파일을 찾을 수 없습니다.");
            }

            // DocumentBuilderFactory를 사용하여 DocumentBuilder 인스턴스를 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // InputStream으로부터 XML 문서를 파싱하여 Document 객체 생성
            Document document = builder.parse(inputStream);
            // 문서의 구조를 정규화 (노드의 표준 형태로 변환)
            document.getDocumentElement().normalize();
            // "query" 태그를 가진 모든 노드를 가져옴
            NodeList nodeList = document.getElementsByTagName("query");

            // 각 "query" 노드를 반복하여 처리
            for (int i = 0; i < nodeList.getLength(); i++) {
                // 현재 노드를 Element로 캐스팅
                Element queryElement = (Element) nodeList.item(i);
                // "id" 속성 값을 가져옴
                String id = queryElement.getAttribute("id");
                // 쿼리의 텍스트 내용을 가져와서 공백을 제거
                String sql = queryElement.getTextContent().trim();
                // ID를 키로, SQL 쿼리를 값으로 하여 맵에 저장
                queries.put(id, sql);
            }
        } catch (Exception e) {
            // 예외 발생 시 RuntimeException으로 감싸서 다시 던짐
            throw new RuntimeException("쿼리 로딩 중 오류 발생", e);
        }
    }

    /**
     * 특정 ID에 해당하는 SQL 쿼리를 반환합니다.
     * 
     * queries.xml 파일에 정의된 쿼리 ID를 통해 해당 SQL 문자열을 조회합니다.
     * 
     * XML 파일 예시:
     * <query id="findUserById">
     *     SELECT * FROM USER WHERE idx = ?
     * </query>
     * 
     * 사용 예시:
     * String sql = QueryUtil.getQuery("findUserById");
     * PreparedStatement pstmt = conn.prepareStatement(sql);
     * pstmt.setInt(1, userId);
     * 
     * @param id XML 파일에 정의된 쿼리 ID
     * @return ID에 해당하는 SQL 쿼리 문자열, 찾지 못한 경우 null
     */
    public static String getQuery(String id) {
        return queries.get(id);
    }
}