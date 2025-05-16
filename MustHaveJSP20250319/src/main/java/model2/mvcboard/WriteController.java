package model2.mvcboard;

import java.io.IOException;

import fileupload.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSFunction;

public class WriteController extends HttpServlet
{
	   private static final long serialVersionUID = 1L;
	   
	   @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		req.getRequestDispatcher("/14MVCBoard/Write.jsp").forward(req, resp);
	}
	   @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		String saveDirectory = req.getServletContext().getRealPath("Uploads");
		
		String originalFileName = "";
		
		try
		{
			originalFileName = FileUtil.uploadFile(req, saveDirectory);
		} catch (Exception e)
		{
			JSFunction.alertLocation(resp, "파일업로드오류입니다.", "../mvcboard/write.do");
			return;
		}
	       MVCBoardDTO dto = new MVCBoardDTO();
	       dto.setName(req.getParameter("name"));
	       dto.setTitle(req.getParameter("title"));
	       dto.setContent(req.getParameter("content"));
	       dto.setPass(req.getParameter("pass"));	
	       
	       if (originalFileName != "") {
	          	// 첩부 파일이 있을 경우 파일명 변경
	          	String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
	          	
	          	dto.setOfile(originalFileName); // 원래 파일 이름
	          	dto.setSfile(savedFileName);	// 서버에 저장된 파일 이름
	          }
	          // 새로운 게시물을 테이블에 입력한다.
	          MVCBoardDAO dao = new MVCBoardDAO();
	          int result = dao.insertWrite(dto);
	          // 커넥션풀 자원반납
	          dao.close();
	          // 서블릿에서 특정 요청명으로 이동할 때는 sendRedirect()를 사용하면 된다.
	          if (result == 1) { 
	          	// 글쓰기에 성공하면 리스트로 이동한다.
	              resp.sendRedirect("../mvcboard/list.do");
	          }
	          else {
	          	// 실패하면 작성페이지로 다시 돌아간다.
	              resp.sendRedirect("../mvcboard/write.do");
	          }
	      }

	}

