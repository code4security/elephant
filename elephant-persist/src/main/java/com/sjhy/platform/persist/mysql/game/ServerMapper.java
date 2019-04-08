<<<<<<< HEAD
package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.Server;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);

    //查询服务器
    Server selectByServer(@Param("gameId") String gameId,@Param("serverId") Integer serverId);
=======
package com.sjhy.platform.persist.mysql.game;

import com.sjhy.platform.client.dto.game.Server;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);

    //查询服务器
    Server selectByServer(@Param("gameId") String gameId,@Param("serverId") Integer serverId);
>>>>>>> fbb421ed9b7b48b40b170e3fe923dd58907356d6
}