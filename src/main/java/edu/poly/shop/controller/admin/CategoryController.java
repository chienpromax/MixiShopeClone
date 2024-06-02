package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.CategoryDto;
import edu.poly.shop.model.Category;
import edu.poly.shop.service.CategoryService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/categories/addOrEdit";
	}

	@GetMapping("edit/{categoryid}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryid") Long categoryId) {
		Optional<Category> opt = categoryService.findById(categoryId);
		CategoryDto dto = new CategoryDto();
		if (opt.isPresent()) {
			Category entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("category", dto);
			return new ModelAndView("admin/categories/addOrEdit", model);
		}
		model.addAttribute("message", "lỗi .");
		return new ModelAndView("forward:/admin/categories", model);
	}

	@GetMapping("delete/{categoryid}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryid") Long categoryId) {
		categoryService.deleteById(categoryId);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@PostMapping("addOrUpdate")
	public ModelAndView addOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}
		if (dto.getCategoryid() == null) {
			Category entity = new Category();
			BeanUtils.copyProperties(dto, entity);
			categoryService.save(entity);
			model.addAttribute("message", "Thêm thành công");
		} else {
			Optional<Category> optionalEntity = categoryService.findById(dto.getCategoryid());
			if (optionalEntity.isPresent()) {
				Category entity = optionalEntity.get();
				BeanUtils.copyProperties(dto, entity);
				categoryService.save(entity);
				model.addAttribute("message", "Cập nhật thành công");
			} else {
				model.addAttribute("message", "Không tìm thấy danh mục để cập nhật");
			}
		}
		return new ModelAndView("redirect:/admin/categories/searchpaginated");

	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Category> list = categoryService.findAll();
		model.addAttribute("categories", list);

		return "admin/categories/list";
	}

	@GetMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name = "nameSearch", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(currentPage-1, pageSize,
				Sort.by("name"));
		Page<Category> resultPage = categoryService.findAll(pageable);

		if (StringUtils.hasText(name)) {
			resultPage = categoryService.findByNameContaining(name, pageable);
			model.addAttribute(name, name);
		} else {
			resultPage = categoryService.findAll(pageable);
		}
		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			if (totalPages > 5) {
				if (end == totalPages) start = end - 5;
				else if (start == 1) end = start + 5;
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("categoryPage", resultPage);
		return "admin/categories/searchpaginated";
	}

}