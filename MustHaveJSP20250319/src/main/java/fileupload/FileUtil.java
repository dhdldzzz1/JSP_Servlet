package fileupload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class FileUtil
{
	// 파일 업로드
	public static String uploadFile(HttpServletRequest req, String sDirectory) 
			throws ServletException, IOException
	{
		// Part 객체를 통해 서버로 전송된 파일명 읽어오기 
		Part part = req.getPart("ofile");
		
		// Part 객체의 헤더값 중 content-disposition 읽어오기  
		String partHeader = part.getHeader("content-disposition");
		//출력결과 => form-data; name="attachedFile"; filename="파일명.jpg"
		System.out.println("partHeader="+ partHeader);
		
		// 헤더값에서 파일명 잘라내기. split() 메서드로 분리한 후 더블쿼테이션을 제거
		String[] phArr = partHeader.split("filename=");
		String orignalFileName = phArr[1].trim().replace("\"", "");
		
		// 전송된 파일이 있다면 디렉토리에 저장
		if(!orignalFileName.isEmpty())
		{
			part.write(sDirectory + File.separator + orignalFileName);
		}
		// 원본 파일명 반환
		return orignalFileName;
	}
	
	// 파일명 변경
	public static String renameFile(String sDirectory, String fileName)
	{
		// 원본파일의 확장자 잘라내기.  
		// lastlndex() 메서드를 사용한 이유는 다음과 같이 파일명
		// 에 점(.)이 2개 이상 포함될 수 있기 때문. ex)2021년.가을하늘.jpg
		String ext = fileName.substring(fileName.lastIndexOf("."));
		// 날짜 및 시간을 통해 "현재날짜_시간" 형식의 문자열을 생성
		String now = new SimpleDateFormat("yyyyMMdd_HmmsS").format(new Date());
		// "날짜_시간.확장자" 형태의 새로운 파일명 생성
		String newFileName = now + ext;
		
		// 객체를 생성 후 기존 파일명을 새로운 파일명으로 변경
		File oldFile = new File(sDirectory + File.separator + fileName);
		File newFile = new File(sDirectory + File.separator + newFileName);
		oldFile.renameTo(newFile);
		
		//변경된 파일명 반환
		return newFileName;
	}
	
	public static ArrayList<String> multipleFile(HttpServletRequest req, String sDirectory) 
			throws ServletException, IOException
	{
		ArrayList<String> listFileName = new ArrayList<>();
		Collection<Part> parts = req.getParts();
		for (Part part : parts)
		{
			if (part.getName().equals("ofile"))
			continue;
			
			String partHeader = part.getHeader("content-dispositoon");
			System.out.println("partHeader=" + partHeader);
			
			
			String[] phArr = partHeader.split("filename=");
			String orinalFileName = phArr[1].trim().replace("\"", "");
			
			if(!orinalFileName.isEmpty())
			{
				part.write(sDirectory + File.separator + orinalFileName);
			}

			listFileName.add(orinalFileName);	
		}
		return listFileName;
	}
}
