package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.OrderDetailDto;
import edu.poly.shop.model.OrderDetail;
import edu.poly.shop.service.OrderDetailService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/orderdetails/")
public class OrderDetailController {

	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("orderdetail", new OrderDetailDto());
		return "admin/orderdetails/addOrEdit";
	}

	@PostMapping("addOrUpdate")
	public ModelAndView addOrUpdate(ModelMap model, @Valid @ModelAttribute("orderdetail") OrderDetail dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/orderdetails/addOrEdit");
		}
		OrderDetail entity = new OrderDetail();
		BeanUtils.copyProperties(dto, entity);

		orderDetailService.save(entity);
		model.addAttribute("message", "Thêm thành công");
		return new ModelAndView("redirect:/admin/orderdetails/searchpaginated", model);
	}

	@GetMapping("edit/{orderDetailid}")
	public ModelAndView edit(ModelMap model, @PathVariable("orderDetailid") Integer orderDetailid) {
		Optional<OrderDetail> opt = orderDetailService.findById(orderDetailid);
		OrderDetailDto dto = new OrderDetailDto();
		if (opt.isPresent()) {
			OrderDetail entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("orderdetail", dto);
			return new ModelAndView("admin/orderdetails/addOrEdit", model);
		}
		model.addAttribute("message", "orderdetails not found.");
		return new ModelAndView("forward:/admin/orderdetails/searchpaginated", model);
	}

	@GetMapping("delete/{orderDetailid}")
	public ModelAndView delete(ModelMap model, @PathVariable("orderDetailid") Integer orderDetailid) {
		orderDetailService.deleteById(orderDetailid);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("forward:/admin/orderdetails/searchpaginated", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<OrderDetail> list = orderDetailService.findAll();
		model.addAttribute("orderdetails", list);
		return "admin/orderdetails/list";
	}

	@GetMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name = "orderid", required = false) Integer orderid,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("orderid"));
		Page<OrderDetail> resultPage;

		if (orderid != null) {
			resultPage = orderDetailService.findByOrderid(orderid, pageable);
			model.addAttribute("orderid", orderid);
		} else {
			resultPage = orderDetailService.findAll(pageable);
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

		model.addAttribute("orderdetailPage", resultPage);
		return "admin/orderdetails/searchpaginated";
	}

}