package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;
import model1.board.BoardDTO;

public class MVCBoardDAO extends DBConnPool
{

   public MVCBoardDAO()
   {
      super();
   }
   
   public int selectCount(Map<String, Object> map)
   {
      int totalCount = 0;
      String query = "SELECT COUNT(*) FROM mvcboard";
      if (map.get("searchWord") != null )
      {
         query += " WHERE " + map.get("searchField") + " "
               + " LIKE '%" + map.get("searchWord") + "%'";
      }
      try
      {
         stmt = con.createStatement();
         rs = stmt.executeQuery(query);
         rs.next();
         totalCount = rs.getInt(1);
         
      } catch (Exception e)
      {
         System.out.println("게시물 카운트 중 예외 발생");
         e.printStackTrace();
      }
      
      return totalCount;
   }
   
   public List<MVCBoardDTO> selectListPage(Map<String, Object> map)
   {
      List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
      
      String query = " " 
                + " SELECT * FROM ( "
                + "    SELECT Tb.*, ROWNUM rNum FROM ( "
                + "       SELECT * FROM mvcboard ";
      
      if (map.get("searchWord") != null) {
         query += " WHERE " + map.get("searchField")
               + " LIKE '%" + map.get("searchWord") + "%' ";
      }
      
      query += "      ORDER BY idx DESC "
            + "    ) Tb "
            + " ) "
            + " WHERE rNum BETWEEN ? AND ?";
      
      try
      {
   
         psmt = con.prepareStatement(query);
         psmt.setString(1, map.get("start").toString());
         psmt.setString(2, map.get("end").toString());
         rs = psmt.executeQuery();
         
         while (rs.next()) {
            MVCBoardDTO dto = new MVCBoardDTO();
            dto.setIdx(rs.getString(1));
            dto.setName(rs.getString(2));
            dto.setTitle(rs.getString(3));
            dto.setContent(rs.getNString(4));
            dto.setPostdate(rs.getDate(5));
            dto.setOfile(rs.getString(6));
            dto.setSfile(rs.getString(7));
            dto.setDowncount(rs.getInt(8));
            dto.setPass(rs.getString(9));
            dto.setVisitcount(rs.getInt(10));
            
            
            board.add(dto);
         }
      } catch (Exception e)
      {
         System.out.println("게시물 조회 중 예외 발생");
         e.printStackTrace();
      }
      return board;
   }
   
   public int insertWrite(MVCBoardDTO dto) {
       int result = 0;
       try {
          String query = "INSERT INTO mvcboard ( "
                   + " idx, name, title, content, ofile, sfile, pass) "
                   + " VALUES ( "
                   + "seq_board_num.NEXTVAL,?,?,?,?,?,?)";
          psmt = con.prepareStatement(query);
          psmt.setString(1, dto.getName());
          psmt.setString(2, dto.getTitle());
          psmt.setString(3, dto.getContent());
          psmt.setString(4, dto.getOfile());
          psmt.setString(5, dto.getSfile());
          psmt.setString(6, dto.getPass());
          result = psmt.executeUpdate();
       }
       catch (Exception e) {
          System.out.println("게시물 입력 중 예외 발생");
          e.printStackTrace();
       }
       return result;
    
	
	}
   	public MVCBoardDTO selecView(String idx)
   	{
   		MVCBoardDTO dto = new MVCBoardDTO();
		
		String query = "SELECT * FROM mvcvoard WHERE idx=?";
		try
		{
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);	// 인파라미터를 일련번호로 설정
			rs = psmt.executeQuery();	// 쿼리문을 실행
				

			if(rs.next())
			{
	            dto.setIdx(rs.getString(1));
	            dto.setName(rs.getString(2));
	            dto.setTitle(rs.getString(3));
	            dto.setContent(rs.getNString(4));
	            dto.setPostdate(rs.getDate(5));
	            dto.setOfile(rs.getString(6));
	            dto.setSfile(rs.getString(7));
	            dto.setDowncount(rs.getInt(8));
	            dto.setPass(rs.getString(9));
	            dto.setVisitcount(rs.getInt(10));
			}
		} catch (Exception e)
		{
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		// 마지막으로 이렇게 완성한 DTO를 반환
		return dto;
	}
	
	// 조회수를 증가시킬 게시물의 일련번호를 매개변수 받음.
    // 게시물의 조회수를 1 증가시킨다.
	public void updateVisitCount(String idx)
	{

		String query = "UPDATE board SET "
				+ " visitcount=visitcount+1 "
				+ " WHERE idx=?";
		
		try
		{
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);

			psmt.executeQuery();
		} catch (Exception e)
		{
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
}

