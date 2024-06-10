package edu.poly.shop.controller.admin;

import java.util.Date;
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
import org.springframework.format.annotation.DateTimeFormat;
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

import edu.poly.shop.domain.OrderDto;
import edu.poly.shop.model.Customer;
import edu.poly.shop.model.Order;
import edu.poly.shop.service.OrderService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/orders/")
public class OrderController {

	@Autowired
	OrderService orderService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("order", new OrderDto());
		return "admin/orders/addOrEdit";
	}

	@PostMapping("addOrUpdate")
	public ModelAndView addOrUpdate(ModelMap model, @Valid @ModelAttribute("order") OrderDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/orders/addOrEdit");
		}
		Order entity = new Order();
		BeanUtils.copyProperties(dto, entity);

		Customer customer = new Customer();
		customer.setCustomerId(dto.getCustomerid());
		entity.setCustomer(customer);

		orderService.save(entity);
		model.addAttribute("message", "Thêm thành công");
		return new ModelAndView("redirect:/admin/orders/searchpaginated", model);
	}

	@GetMapping("edit/{orderid}")
	public ModelAndView edit(ModelMap model, @PathVariable("orderid") Integer orderid) {
		Optional<Order> opt = orderService.findById(orderid);
		OrderDto dto = new OrderDto();
		if (opt.isPresent()) {
			Order entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setCustomerid(entity.getCustomer().getCustomerId());
			dto.setIsEdit(true);
			model.addAttribute("order", dto);
			return new ModelAndView("admin/orders/addOrEdit", model);
		}
		model.addAttribute("message", "Order not found.");
		return new ModelAndView("forward:/admin/orders/searchpaginated", model);
	}

	@GetMapping("delete/{orderid}")
	public ModelAndView delete(ModelMap model, @PathVariable("orderid") Integer orderid) {
		orderService.deleteById(orderid);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("forward:/admin/orders/searchpaginated", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Order> list = orderService.findAll();
		model.addAttribute("orders", list);
		return "admin/orders/list";
	}

	@GetMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name = "dateSearch", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("orderDate"));
		Page<Order> resultPage;

		if (orderDate != null) {
			resultPage = orderService.findByOrderDate(orderDate, pageable);
			model.addAttribute("dateSearch", orderDate);
		} else {
			resultPage = orderService.findAll(pageable);
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

		model.addAttribute("orderPage", resultPage);
		return "admin/orders/searchpaginated";
	}

}