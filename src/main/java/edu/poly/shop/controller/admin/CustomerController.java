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

import edu.poly.shop.domain.CustomerDto;
import edu.poly.shop.model.Customer;
import edu.poly.shop.service.CustomerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("customer", new CustomerDto());
		return "admin/customers/addOrEdit";
	}

	@PostMapping("addOrUpdate")
	public ModelAndView addOrUpdate(ModelMap model, @Valid @ModelAttribute("customer") CustomerDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
		return new ModelAndView("admin/customers/addOrEdit");
		}
		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity);
		customerService.save(entity);
		model.addAttribute("message", "Thêm thành công");
		return new ModelAndView("redirect:/admin/customers/searchpaginated", model);
	}

	@GetMapping("edit/{customerid}")
	public ModelAndView edit(ModelMap model, @PathVariable("customerid") Long
	customerid) {
	Optional<Customer> opt = customerService.findById(customerid);
	CustomerDto dto = new CustomerDto();
	if (opt.isPresent()) {
	Customer entity = opt.get();
	BeanUtils.copyProperties(entity, dto);
	dto.setIsEdit(true);
	model.addAttribute("customer", dto);
	return new ModelAndView("admin/customers/addOrEdit", model);
	}
	model.addAttribute("message", "lỗi .");
	return new ModelAndView("forward:/admin/customers/searchpaginated", model);
	}

	@GetMapping("delete/{customerid}")
	public ModelAndView delete(ModelMap model, @PathVariable("customerid") Long customerid) {
		customerService.deleteById(customerid);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("forward:/admin/customers/searchpaginated", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Customer> list = customerService.findAll();
		model.addAttribute("customers", list);
		return "admin/customers/list";
	}

	@GetMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name = "nameSearch", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize,
				Sort.by("fullName"));
		Page<Customer> resultPage = customerService.findAll(pageable);

		if (StringUtils.hasText(name)) {
			resultPage = customerService.findByFullNameContaining(name, pageable);
			model.addAttribute(name, name);
		} else {
			resultPage = customerService.findAll(pageable);
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

		model.addAttribute("customerPage", resultPage);
		return "admin/customers/searchpaginated";
	}

}