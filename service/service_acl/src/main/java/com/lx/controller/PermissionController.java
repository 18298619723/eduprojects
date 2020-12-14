package com.lx.controller;


import com.lx.entity.Permission;
import com.lx.service.PermissionService;
import com.lx.pojo.RC;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public RC indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return RC.success().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public RC remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return RC.success();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public RC doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return RC.success();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public RC toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return RC.success().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public RC save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return RC.success();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public RC updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return RC.success();
    }

}

