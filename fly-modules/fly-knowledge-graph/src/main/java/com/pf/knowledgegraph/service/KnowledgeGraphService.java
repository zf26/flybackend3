package com.pf.knowledgegraph.service;

import com.pf.knowledgegraph.domain.graph.*;
import com.pf.knowledgegraph.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 知识图谱查询服务
 *
 * @author fly
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KnowledgeGraphService {

    private final SymptomRepository symptomRepository;
    private final DiseaseRepository diseaseRepository;
    private final DrugRepository drugRepository;
    private final RecipeRepository recipeRepository;
    private final DepartmentRepository departmentRepository;
    private final ExaminationMethodRepository examinationRepository;

    // ========== 症状相关查询 ==========

    /**
     * 根据症状名称查询症状信息
     */
    public Optional<SymptomNode> findSymptomByName(String name) {
        return symptomRepository.findByName(name);
    }

    /**
     * 根据症状ID查询相关疾病
     */
    public List<DiseaseNode> findRelatedDiseases(Long symptomId) {
        return symptomRepository.findRelatedDiseases(symptomId);
    }

    /**
     * 搜索症状（模糊匹配）
     */
    public List<SymptomNode> searchSymptoms(String keyword) {
        return symptomRepository.findByNameContaining(keyword);
    }


    // ========== 疾病相关查询 ==========

    /**
     * 根据疾病名称查询疾病信息
     */
    public Optional<DiseaseNode> findDiseaseByName(String name) {
        return diseaseRepository.findByName(name);
    }

    /**
     * 根据疾病ID查询相关症状
     */
    public List<SymptomNode> findRelatedSymptoms(String diseaseId) {
        return diseaseRepository.findRelatedSymptoms(diseaseId);
    }

    /**
     * 根据疾病ID查询并发症
     */
    public List<DiseaseNode> findDiseaseComplications(String diseaseId) {
        return diseaseRepository.findComplications(diseaseId);
    }

    /**
     * 根据疾病ID查询所属科室
     */
    public List<DepartmentNode> findDiseaseDepartments(String diseaseId) {
        return diseaseRepository.findRelatedDepartments(diseaseId);
    }

    /**
     * 根据疾病ID查询推荐药物
     */
    public List<DrugNode> findRecommendedDrugs(String diseaseId) {
        return diseaseRepository.findRecommendedDrugs(diseaseId);
    }

    /**
     * 根据疾病ID查询推荐食谱
     */
    public List<RecipeNode> findRecommendedRecipes(String diseaseId) {
        return diseaseRepository.findRecommendedRecipes(diseaseId);
    }

    /**
     * 根据疾病ID查询宜吃食物
     */
    public List<FoodNode> findRecommendedFoods(String diseaseId) {
        return diseaseRepository.findRecommendedFoods(diseaseId);
    }

    /**
     * 根据疾病ID查询忌吃食物
     */
    public List<FoodNode> findAvoidFoods(String diseaseId) {
        return diseaseRepository.findAvoidFoods(diseaseId);
    }

    /**
     * 根据疾病ID查询诊断检查
     */
    public List<ExaminationMethodNode> findDiagnosticExaminations(String diseaseId) {
        return diseaseRepository.findDiagnosticExaminations(diseaseId);
    }

    /**
     * 搜索疾病（模糊匹配）
     */
    public List<DiseaseNode> searchDiseases(String keyword) {
        return diseaseRepository.findByNameContaining(keyword);
    }



    // ========== 药物相关查询 ==========

    /**
     * 根据药物名称查询药物信息
     */
    public Optional<DrugNode> findDrugByName(String name) {
        return drugRepository.findByName(name);
    }

    /**
     * 根据药物ID查询生产商
     */
    public List<ManufacturerNode> findDrugManufacturers(String drugId) {
        return drugRepository.findManufacturersByDrug(drugId);
    }

    /**
     * 根据生产商ID查询生产的药物
     */
    public List<DrugNode> findManufacturerDrugs(String manufacturerId) {
        return drugRepository.findDrugsByManufacturer(manufacturerId);
    }

    /**
     * 查找替代药物
     */
    public List<DrugNode> findAlternativeDrugs(String drugId) {
        return drugRepository.findAlternativeDrugs(drugId);
    }

    /**
     * 搜索药物（模糊匹配）
     */
    public List<DrugNode> searchDrugs(String keyword) {
        return drugRepository.findByNameContaining(keyword);
    }

    // ========== 食谱相关查询 ==========

    /**
     * 根据食谱名称查询食谱信息
     */
    public Optional<RecipeNode> findRecipeByName(String name) {
        return recipeRepository.findByName(name);
    }


    /**
     * 搜索食谱（模糊匹配）
     */
    public List<RecipeNode> searchRecipes(String keyword) {
        return recipeRepository.findByNameContaining(keyword);
    }

    // ========== 科室相关查询 ==========

    /**
     * 根据科室名称查询科室信息
     */
    public Optional<DepartmentNode> findDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    /**
     * 获取所有一级科室
     */
    public List<DepartmentNode> getPrimaryDepartments() {
        return departmentRepository.findPrimaryDepartments();
    }

    /**
     * 获取所有二级科室
     */
    public List<DepartmentNode> getSecondaryDepartments() {
        return departmentRepository.findSecondaryDepartments();
    }

    /**
     * 根据二级科室ID查找上级一级科室
     */
    public Optional<DepartmentNode> findParentDepartment(String departmentId) {
        return departmentRepository.findParentDepartment(departmentId);
    }

    /**
     * 根据一级科室ID查找下级二级科室
     */
    public List<DepartmentNode> findChildDepartments(String departmentId) {
        return departmentRepository.findChildDepartments(departmentId);
    }

    // ========== 检查手段相关查询 ==========

    /**
     * 根据检查名称查询检查信息
     */
    public Optional<ExaminationMethodNode> findExaminationByName(String name) {
        return examinationRepository.findByName(name);
    }

    /**
     * 根据检查ID查询相关疾病
     */
    public List<DiseaseNode> findExaminationRelatedDiseases(String examId) {
        return examinationRepository.findRelatedDiseases(examId);
    }

    /**
     * 查找相关的检查手段
     */
    public List<ExaminationMethodNode> findRelatedExaminations(String examId) {
        return examinationRepository.findRelatedExaminations(examId);
    }

    /**
     * 搜索检查手段（模糊匹配）
     */
    public List<ExaminationMethodNode> searchExaminations(String keyword) {
        return examinationRepository.findByNameContaining(keyword);
    }

    // ========== 综合查询 ==========

    /**
     * 综合搜索（跨节点类型）
     */
    public Map<String, List<?>> comprehensiveSearch(String keyword) {
        return Map.of(
            "symptoms", searchSymptoms(keyword),
            "diseases", searchDiseases(keyword),
            "drugs", searchDrugs(keyword),
            "recipes", searchRecipes(keyword),
            "examinations", searchExaminations(keyword)
        );
    }

    /**
     * 获取图谱统计信息
     */
    public Map<String, Object> getGraphStatistics() {
        return Map.of(
            "symptomCount", symptomRepository.count(),
            "diseaseCount", diseaseRepository.count(),
            "drugCount", drugRepository.count(),
            "recipeCount", recipeRepository.count(),
            "departmentCount", departmentRepository.count(),
            "examinationCount", examinationRepository.count()
        );
    }

    /**
     * 根据症状描述进行智能诊断
     */
    public Map<String, Object> diagnoseBySymptoms(List<String> symptomNames) {
        // 这里可以实现更复杂的诊断逻辑
        // 目前返回简单的统计信息
        return Map.of(
            "inputSymptoms", symptomNames,
            "possibleDiseases", "待实现",
            "recommendedExaminations", "待实现",
            "suggestedTreatments", "待实现"
        );
    }

    /**
     * 根据症状ID获取综合信息
     * 包含相关疾病、一级科室、二级科室、药物、食谱、食物、检查手段、治疗方案
     */
    public Map<String, Object> getSymptomComprehensiveInfo(Long symptomId) {
        // 获取相关疾病
        List<DiseaseNode> diseases = findRelatedDiseases(symptomId);

        // 获取相关科室信息（通过疾病获取）
        List<DepartmentNode> primaryDepartments = diseases.stream()
                .flatMap(disease -> findDiseaseDepartments(disease.getId()).stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        List<DepartmentNode> secondaryDepartments = primaryDepartments.stream()
                .flatMap(primaryDept -> findChildDepartments(primaryDept.getId()).stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        // 获取推荐药物（通过疾病获取）
        List<DrugNode> drugs = diseases.stream()
                .flatMap(disease -> findRecommendedDrugs(disease.getId()).stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        // 获取推荐食谱（通过疾病获取）
        List<RecipeNode> recipes = diseases.stream()
                .flatMap(disease -> findRecommendedRecipes(disease.getId()).stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        // 获取推荐食物（通过疾病获取）
        List<FoodNode> recommendedFoods = diseases.stream()
                .flatMap(disease -> findRecommendedFoods(disease.getId()).stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        // 获取忌吃食物（通过疾病获取）
        List<FoodNode> avoidFoods = diseases.stream()
                .flatMap(disease -> findAvoidFoods(disease.getId()).stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        // 获取检查手段（通过疾病获取）
        List<ExaminationMethodNode> examinations = diseases.stream()
                .flatMap(disease -> findDiagnosticExaminations(disease.getId()).stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        // 合并食物列表
        List<FoodNode> allFoods = new java.util.ArrayList<>();
        allFoods.addAll(recommendedFoods);
        allFoods.addAll(avoidFoods.stream()
                .filter(food -> !recommendedFoods.contains(food))
                .collect(java.util.stream.Collectors.toList()));

        // 获取治疗方案（目前返回空，后续可以扩展）
        List<String> treatmentPlans = new java.util.ArrayList<>();

        return Map.of(
            "diseases", diseases,
            "primaryDepartments", primaryDepartments,
            "secondaryDepartments", secondaryDepartments,
            "drugs", drugs,
            "recipes", recipes,
            "foods", allFoods,
            "recommendedFoods", recommendedFoods,
            "avoidFoods", avoidFoods,
            "examinations", examinations,
            "treatmentPlans", treatmentPlans
        );
    }
}

