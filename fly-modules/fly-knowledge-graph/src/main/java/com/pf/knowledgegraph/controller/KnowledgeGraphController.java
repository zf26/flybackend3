package com.pf.knowledgegraph.controller;

import com.pf.common.core.domain.R;
import com.pf.knowledgegraph.domain.graph.*;
import com.pf.knowledgegraph.service.KnowledgeGraphService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 知识图谱查询控制器
 *
 * @author fly
 */
@Tag(name = "知识图谱查询", description = "知识图谱相关查询接口")
@RestController
@RequestMapping("/knowledge-graph")
@RequiredArgsConstructor
public class KnowledgeGraphController {

    @Autowired
    private KnowledgeGraphService knowledgeGraphService;

    // ========== 症状相关接口 ==========

    @Operation(summary = "根据症状名称查询症状信息")
    @GetMapping("/symptom/name/{name}")
    public R<SymptomNode> getSymptomByName(
            @Parameter(description = "症状名称") @PathVariable String name) {
        return R.ok(knowledgeGraphService.findSymptomByName(name).orElse(null));
    }

    @Operation(summary = "根据症状ID查询相关疾病")
    @GetMapping("/symptom/{symptomId}/diseases")
    public R<List<DiseaseNode>> getRelatedDiseases(
            @Parameter(description = "症状ID") @PathVariable Long symptomId) {
        return R.ok(knowledgeGraphService.findRelatedDiseases(symptomId));
    }


    @Operation(summary = "搜索症状")
    @GetMapping("/search/symptoms")
    public R<List<SymptomNode>> searchSymptoms(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        return R.ok(knowledgeGraphService.searchSymptoms(keyword));
    }

    // ========== 疾病相关接口 ==========

    @Operation(summary = "根据疾病名称查询疾病信息")
    @GetMapping("/disease/name/{name}")
    public R<DiseaseNode> getDiseaseByName(
            @Parameter(description = "疾病名称") @PathVariable String name) {
        return R.ok(knowledgeGraphService.findDiseaseByName(name).orElse(null));
    }

    @Operation(summary = "根据疾病ID查询相关症状")
    @GetMapping("/disease/{diseaseId}/symptoms")
    public R<List<SymptomNode>> getRelatedSymptoms(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findRelatedSymptoms(diseaseId));
    }

    @Operation(summary = "根据疾病ID查询并发症")
    @GetMapping("/disease/{diseaseId}/complications")
    public R<List<DiseaseNode>> getDiseaseComplications(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findDiseaseComplications(diseaseId));
    }

    @Operation(summary = "根据疾病ID查询所属科室")
    @GetMapping("/disease/{diseaseId}/departments")
    public R<List<DepartmentNode>> getDiseaseDepartments(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findDiseaseDepartments(diseaseId));
    }

    @Operation(summary = "根据疾病ID查询推荐药物")
    @GetMapping("/disease/{diseaseId}/drugs")
    public R<List<DrugNode>> getRecommendedDrugs(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findRecommendedDrugs(diseaseId));
    }

    @Operation(summary = "根据疾病ID查询推荐食谱")
    @GetMapping("/disease/{diseaseId}/recipes")
    public R<List<RecipeNode>> getRecommendedRecipes(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findRecommendedRecipes(diseaseId));
    }

    @Operation(summary = "根据疾病ID查询宜吃食物")
    @GetMapping("/disease/{diseaseId}/foods/recommended")
    public R<List<FoodNode>> getRecommendedFoods(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findRecommendedFoods(diseaseId));
    }

    @Operation(summary = "根据疾病ID查询忌吃食物")
    @GetMapping("/disease/{diseaseId}/foods/avoid")
    public R<List<FoodNode>> getAvoidFoods(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findAvoidFoods(diseaseId));
    }

    @Operation(summary = "根据疾病ID查询诊断检查")
    @GetMapping("/disease/{diseaseId}/examinations")
    public R<List<ExaminationMethodNode>> getDiagnosticExaminations(
            @Parameter(description = "疾病ID") @PathVariable String diseaseId) {
        return R.ok(knowledgeGraphService.findDiagnosticExaminations(diseaseId));
    }

    @Operation(summary = "搜索疾病")
    @GetMapping("/search/diseases")
    public R<List<DiseaseNode>> searchDiseases(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        return R.ok(knowledgeGraphService.searchDiseases(keyword));
    }

    // ========== 药物相关接口 ==========

    @Operation(summary = "根据药物名称查询药物信息")
    @GetMapping("/drug/name/{name}")
    public R<DrugNode> getDrugByName(
            @Parameter(description = "药物名称") @PathVariable String name) {
        return R.ok(knowledgeGraphService.findDrugByName(name).orElse(null));
    }

    @Operation(summary = "根据药物ID查询生产商")
    @GetMapping("/drug/{drugId}/manufacturers")
    public R<List<ManufacturerNode>> getDrugManufacturers(
            @Parameter(description = "药物ID") @PathVariable String drugId) {
        return R.ok(knowledgeGraphService.findDrugManufacturers(drugId));
    }

    @Operation(summary = "查找替代药物")
    @GetMapping("/drug/{drugId}/alternatives")
    public R<List<DrugNode>> getAlternativeDrugs(
            @Parameter(description = "药物ID") @PathVariable String drugId) {
        return R.ok(knowledgeGraphService.findAlternativeDrugs(drugId));
    }

    @Operation(summary = "搜索药物")
    @GetMapping("/search/drugs")
    public R<List<DrugNode>> searchDrugs(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        return R.ok(knowledgeGraphService.searchDrugs(keyword));
    }

    // ========== 食谱相关接口 ==========

    @Operation(summary = "根据食谱名称查询食谱信息")
    @GetMapping("/recipe/name/{name}")
    public R<RecipeNode> getRecipeByName(
            @Parameter(description = "食谱名称") @PathVariable String name) {
        return R.ok(knowledgeGraphService.findRecipeByName(name).orElse(null));
    }


    @Operation(summary = "搜索食谱")
    @GetMapping("/search/recipes")
    public R<List<RecipeNode>> searchRecipes(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        return R.ok(knowledgeGraphService.searchRecipes(keyword));
    }

    // ========== 科室相关接口 ==========

    @Operation(summary = "根据科室名称查询科室信息")
    @GetMapping("/department/name/{name}")
    public R<DepartmentNode> getDepartmentByName(
            @Parameter(description = "科室名称") @PathVariable String name) {
        return R.ok(knowledgeGraphService.findDepartmentByName(name).orElse(null));
    }

    @Operation(summary = "获取所有一级科室")
    @GetMapping("/departments/primary")
    public R<List<DepartmentNode>> getPrimaryDepartments() {
        return R.ok(knowledgeGraphService.getPrimaryDepartments());
    }

    @Operation(summary = "获取所有二级科室")
    @GetMapping("/departments/secondary")
    public R<List<DepartmentNode>> getSecondaryDepartments() {
        return R.ok(knowledgeGraphService.getSecondaryDepartments());
    }

    @Operation(summary = "根据二级科室ID查找上级一级科室")
    @GetMapping("/department/{departmentId}/parent")
    public R<DepartmentNode> getParentDepartment(
            @Parameter(description = "科室ID") @PathVariable String departmentId) {
        return R.ok(knowledgeGraphService.findParentDepartment(departmentId).orElse(null));
    }

    @Operation(summary = "根据一级科室ID查找下级二级科室")
    @GetMapping("/department/{departmentId}/children")
    public R<List<DepartmentNode>> getChildDepartments(
            @Parameter(description = "科室ID") @PathVariable String departmentId) {
        return R.ok(knowledgeGraphService.findChildDepartments(departmentId));
    }

    // ========== 检查手段相关接口 ==========

    @Operation(summary = "根据检查名称查询检查信息")
    @GetMapping("/examination/name/{name}")
    public R<ExaminationMethodNode> getExaminationByName(
            @Parameter(description = "检查名称") @PathVariable String name) {
        return R.ok(knowledgeGraphService.findExaminationByName(name).orElse(null));
    }

    @Operation(summary = "根据检查ID查询相关疾病")
    @GetMapping("/examination/{examId}/diseases")
    public R<List<DiseaseNode>> getExaminationRelatedDiseases(
            @Parameter(description = "检查ID") @PathVariable String examId) {
        return R.ok(knowledgeGraphService.findExaminationRelatedDiseases(examId));
    }

    @Operation(summary = "查找相关的检查手段")
    @GetMapping("/examination/{examId}/related")
    public R<List<ExaminationMethodNode>> getRelatedExaminations(
            @Parameter(description = "检查ID") @PathVariable String examId) {
        return R.ok(knowledgeGraphService.findRelatedExaminations(examId));
    }

    @Operation(summary = "搜索检查手段")
    @GetMapping("/search/examinations")
    public R<List<ExaminationMethodNode>> searchExaminations(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        return R.ok(knowledgeGraphService.searchExaminations(keyword));
    }

    // ========== 综合查询接口 ==========

    @Operation(summary = "综合搜索")
    @GetMapping("/search/comprehensive")
    public R<Map<String, List<?>>> comprehensiveSearch(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        return R.ok(knowledgeGraphService.comprehensiveSearch(keyword));
    }

    @Operation(summary = "根据症状描述进行智能诊断")
    @PostMapping("/diagnose")
    public R<Map<String, Object>> diagnoseBySymptoms(
            @Parameter(description = "症状名称列表") @RequestBody List<String> symptomNames) {
        return R.ok(knowledgeGraphService.diagnoseBySymptoms(symptomNames));
    }

    @Operation(summary = "获取图谱统计信息")
    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(knowledgeGraphService.getGraphStatistics());
    }

    // ========== 核心业务接口 ==========

    @Operation(summary = "根据症状ID获取综合信息")
    @GetMapping("/symptom/{symptomId}/comprehensive")
    public R<Map<String, Object>> getSymptomComprehensiveInfo(
            @Parameter(description = "症状ID") @PathVariable Long symptomId) {
        return R.ok(knowledgeGraphService.getSymptomComprehensiveInfo(symptomId));
    }
}
