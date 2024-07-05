package edu.poly.shop.controller.admin;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.ProductDto;
import edu.poly.shop.model.Product;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.utils.UploadUtils;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/products")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("product", new ProductDto());
		return "admin/products/addOrEdit";
	}

	@PostMapping("addOrUpdate")
	public ModelAndView addOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("product", dto);
			return new ModelAndView("admin/products/addOrEdit", model);
		}
		Product entity = new Product();
		BeanUtils.copyProperties(dto, entity);
		// Xử lý tải lên tệp
		MultipartFile imageFile = dto.getImageFile();
		if (imageFile != null && !imageFile.isEmpty()) {
			try {
				String uploadDir = "src/main/resources/static/uploads/";
				String originalFilename = imageFile.getOriginalFilename();
				String newFilename = UploadUtils.saveFile(uploadDir, originalFilename, imageFile);
				entity.setImage(newFilename);
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("message", "Lỗi khi tải tệp: " + e.getMessage());
				return new ModelAndView("admin/products/addOrEdit", model);
			}
		} else {
			// Giữ lại tên ảnh hiện tại
			Optional<Product> existingProductOpt = productService.findById(dto.getProductid());
			if (existingProductOpt.isPresent()) {
				entity.setImage(existingProductOpt.get().getImage());
			}
		}

		productService.save(entity);
		model.addAttribute("message", "Cập nhật thành công");
		return new ModelAndView("redirect:/admin/products/searchpaginated", model);
	}

	@GetMapping("edit/{productid}")
	public ModelAndView edit(ModelMap model, @PathVariable("productid") Long productId) {
		Optional<Product> opt = productService.findById(productId);
		ProductDto dto = new ProductDto();
		if (opt.isPresent()) {
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("product", dto);
			System.out.println("=" + dto);
			return new ModelAndView("admin/products/addOrEdit", model);
		}
		model.addAttribute("message", "lỗi .");
		return new ModelAndView("forward:/admin/products/searchpaginated", model);
	}

	@GetMapping("delete/{productid}")
	public ModelAndView delete(ModelMap model, @PathVariable("productid") Long productId) {
		productService.deleteById(productId);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("forward:/admin/products/searchpaginated", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Product> list = productService.findAll();
		model.addAttribute("products", list);
		return "admin/products/list";
	}

	@GetMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name = "nameSearch", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize,
				Sort.by("name"));
		Page<Product> resultPage = productService.findAll(pageable);

		if (StringUtils.hasText(name)) {
			resultPage = productService.findByNameContaining(name, pageable);
			model.addAttribute(name, name);
		} else {
			resultPage = productService.findAll(pageable);
		}
		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			if (totalPages > 5) {
				if (end == totalPages)
					start = end - 5;
				else if (start == 1)
					end = start + 5;
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("productPage", resultPage);
		return "admin/products/searchpaginated";
	}
}