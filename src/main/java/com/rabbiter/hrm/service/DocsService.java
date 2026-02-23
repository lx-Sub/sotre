package com.rabbiter.hrm.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbiter.hrm.dto.Response;
import com.rabbiter.hrm.dto.ResponseDTO;
import com.rabbiter.hrm.entity.Docs;
import com.rabbiter.hrm.enums.BusinessStatusEnum;
import com.rabbiter.hrm.exception.ServiceException;
import com.rabbiter.hrm.mapper.DocsMapper;
import com.rabbiter.hrm.util.HutoolExcelUtil;
import com.rabbiter.hrm.util.PathUtils;
import com.rabbiter.hrm.vo.StaffDocsVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date 2024/2/24
 * @Version 1.0
 */
@Service
public class DocsService extends ServiceImpl<DocsMapper, Docs> {

    @Resource
    private DocsMapper docsMapper;

    private String folderPath = PathUtils.getClassLoadRootPath() + "/files/";
    /**
     * 文件上传
     *
     * @param uploadFile
     * @return
     */
    public ResponseDTO upload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        String token = request.getHeader("token"); // 从 http 请求头中取出 token
        if (StrUtil.isNotBlank(token)) {
            Integer staffId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs(); // 创建文件夹
            }

            // 判断文件是否为空
            if (!uploadFile.isEmpty()) {
                String originalFilename = uploadFile.getOriginalFilename(); // 获取文件原名称
                String extName = FileUtil.extName(originalFilename); // 获取文件扩展名
                String filename = IdUtil.fastSimpleUUID().substring(2, 22) + "." + extName; // 生成新文件名

                // 获取文件的md5信息
                String md5 = SecureUtil.md5(uploadFile.getInputStream());
                List<Docs> docsList = list(new QueryWrapper<Docs>().eq("md5", md5));

                // 若文件已存在，则使用原文件名
                if (docsList != null && docsList.size() > 0) {
                    filename = docsList.get(0).getName();
                } else {
                    File file = new File(folderPath + filename);
                    uploadFile.transferTo(file); // 将文件存储到磁盘
                }

                Docs docs = new Docs();
                docs.setName(filename);
                docs.setStaffId(staffId); // 文件上传者
                docs.setType(extName); // 文件类型
                docs.setOldName(originalFilename); // 文件原名称
                docs.setMd5(md5); // 文件的md5
                docs.setSize(uploadFile.getSize() / 1024); // 文件大小（KB）

                if (save(docs)) {
                    return Response.success("文件上传成功！", docs);
                }
                throw new ServiceException(BusinessStatusEnum.ERROR);
            }
            throw new ServiceException(BusinessStatusEnum.FILE_NOT_EXIST);
        }
        throw new ServiceException(BusinessStatusEnum.TOKEN_NOT_EXIST);
    }



    /**
     * 文件下载
     *
     * @param filename
     * @param response
     * @return
     * @throws IOException
     */
    public ResponseDTO download(String filename, HttpServletResponse response) throws IOException {
        // 通知浏览器以下载的方式打开
        response.addHeader("Content-Type", "application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        // 通过文件流读取文件
        File downloadFile = new File(folderPath + filename);
        OutputStream out = response.getOutputStream();
        // 读取文件的字节流
        out.write(FileUtil.readBytes(downloadFile));
        out.flush();
        out.close();
        return Response.success();
    }


    public ResponseDTO add(Docs docs) {
        if (save(docs)) {
            return Response.success();
        }
        return Response.error();
    }

    public ResponseDTO deleteById(Integer id) {
        if (removeById(id)) {
            return Response.success();
        }
        return Response.error();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO deleteBatch(List<Integer> ids) {
        if (removeBatchByIds(ids)) {
            return Response.success();
        }
        return Response.error();
    }


    public ResponseDTO edit(Docs docs) {
        if (updateById(docs)) {
            return Response.success();
        }
        return Response.error();
    }


    public ResponseDTO findById(Integer id) {
        Docs docs = getById(id);
        if (docs != null) {
            return Response.success(docs);
        }
        return Response.error();
    }


    public ResponseDTO list(Integer current, Integer size, String oldName, String staffName) {
        if (oldName == null) {
            oldName = "";
        }
        if(staffName == null){
            staffName = "";
        }
        IPage<StaffDocsVO> config = new Page<>(current, size);
        IPage<StaffDocsVO> page = this.docsMapper.listStaffDocsVO(config, oldName,staffName);
        // 将响应数据填充到map中
        Map map = new HashMap();
        map.put("pages", page.getPages());
        map.put("total", page.getTotal());
        map.put("list", page.getRecords());
        return Response.success(map);
    }

    public ResponseDTO export(HttpServletResponse response) throws IOException {
        List<Docs> list = list();
        HutoolExcelUtil.writeExcel(response, list, "文件信息表", Docs.class);
        return Response.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<Docs> list = HutoolExcelUtil.readExcel(inputStream, 1, Docs.class);
        // IService接口中的方法.批量插入数据
        if (saveBatch(list)) {
            return Response.success();
        }
        return Response.error();
    }


}
