package com.rabbiter.hrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.hrm.entity.MaterialDeclaration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @Author
 * @since 2024-03-29
 */
@Mapper
public interface MaterialDeclarationMapper extends BaseMapper<MaterialDeclaration> {

    /**
     * 根据审批人、材料名称和申请日期查找材料申报信息
     * 使用分页查询
     */
    @Select("<script>" +
            "SELECT * FROM material_declaration " +
            "WHERE 1=1 " +
            "<if test='approver != null'> AND approver LIKE CONCAT('%', #{approver}, '%') </if>" +
            "<if test='materialName != null'> AND material_name LIKE CONCAT('%', #{materialName}, '%') </if>" +
            "<if test='applyDate != null'> AND DATE_FORMAT(applyDate, '%Y-%m') = #{applyDate} </if>" +
            "ORDER BY applyDate DESC " +
            "LIMIT #{pageSize} OFFSET #{offset} " +
            "</script>")
    List<MaterialDeclaration> findAllByConditions(@Param("approver") String approver,
                                                  @Param("materialName") String materialName,
                                                  @Param("applyDate") String applyDate,
                                                  @Param("offset") int offset,
                                                  @Param("pageSize") int pageSize);

    /**
     * 统计所有材料申报记录数量
     * @param approver 审批人
     * @param materialName 材料名称
     * @param applyDate 申请日期
     * @return
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM material_declaration " +
            "WHERE 1=1  " +
            "<if test='approver != null'> AND approver LIKE CONCAT('%', #{approver}, '%') </if>" +
            "<if test='materialName != null'> AND material_name LIKE CONCAT('%', #{materialName}, '%') </if>" +
            "<if test='applyDate != null'> AND DATE_FORMAT(applyDate, '%Y-%m') = #{applyDate} </if>" +
            "</script>")
    Integer countByConditions(@Param("approver") String approver,
                              @Param("materialName") String materialName,
                              @Param("applyDate") String applyDate);

    /**
     * 根据材料ID查询材料申报信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM material_declaration WHERE id = #{id} ")
    MaterialDeclaration findById(@Param("id") Long id);

    /**
     * 更新材料申报的审批状态
     * @param id
     * @param status
     * @return
     */
    @Update("UPDATE material_declaration SET status = #{status} WHERE id = #{id} ")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
