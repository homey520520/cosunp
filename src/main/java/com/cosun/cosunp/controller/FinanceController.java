package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFinanceServ;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/17 下午 4:28
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    private static Logger logger = LogManager.getLogger(FinanceController.class);

    @Autowired
    IFinanceServ financeServ;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    IPersonServ personServ;

    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView tomainpage(HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView("salaryimport");
            EmpHours empHours = new EmpHours();
            view.addObject("empHours", empHours);
            view.addObject("financeImportData", new FinanceImportData());
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/toCompute")
    public ModelAndView toCompute(HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView("salarycompute");
            view.addObject("financeImportData",new FinanceImportData());
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toupdatesalary")
    public ModelAndView toupdatesalary(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("financeappli");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/computeSalary", method = RequestMethod.POST)
    public ModelAndView computeSalary( FinanceImportData financeImportData,HttpServletResponse resp) throws Exception {
        try {
            ModelAndView view = new ModelAndView("salarycompute");
            List<SalaryDataOutPut> salaryDataOutPuts = financeServ.computeSalaryData(financeImportData.getYearMonth());
            String outpathname = "计算完成，请下载查看!";
            if(salaryDataOutPuts.size()>0) {
                if (salaryDataOutPuts.get(0).getErrorMessage() == null || salaryDataOutPuts.get(0).getErrorMessage().trim().length() <= 0) {
                    financeServ.saveSalaryDataOutPutsList(salaryDataOutPuts,financeImportData.getYearMonth());
                    resp.setHeader("content-type", "application/octet-stream");
                    resp.setContentType("application/octet-stream");
                    List<String> pathName = ExcelUtil.writeExcelSalary(salaryDataOutPuts, financeImportData.getYearMonth(), finalDirPath);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + new String(pathName.get(0).getBytes(), "iso-8859-1"));
                    byte[] buff = new byte[1024];
                    BufferedInputStream bufferedInputStream = null;
                    OutputStream outputStream = null;
                    try {
                        outputStream = resp.getOutputStream();
                        File fi = new File(pathName.get(1));
                        FileInputStream fis = new FileInputStream(fi);
                        bufferedInputStream = new BufferedInputStream(fis);
                        int num = bufferedInputStream.read(buff);
                        while (num != -1) {
                            outputStream.write(buff, 0, num);
                            outputStream.flush();
                            num = bufferedInputStream.read(buff);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    } finally {
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                            outputStream.close();
                        }
                    }
                }
                view.addObject("flag", outpathname);
                view.addObject("financeImportData",financeImportData );
                view.addObject("errorMessage", salaryDataOutPuts.get(0).getErrorMessage());
                return view;
            }
            view.addObject("flag", outpathname);
            view.addObject("financeImportData",financeImportData );
            view.addObject("errorMessage", "空文件");
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toaddpersonsalarypage", method = RequestMethod.GET)
    public ModelAndView toaddpersonsalarypage(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("addfinance");
        Employee employee = new Employee();
        List<Employee> empList = personServ.findAllEmployeeAll();
        view.addObject("employee", employee);
        view.addObject("empList", empList);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/checkFinanceImportNoandYearMonthIsExsit", method = RequestMethod.POST)
    public void checkFinanceImportNoandYearMonthIsExsit(EmpHours empHours, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<Employee> employees = new ArrayList<Employee>();
            Employee employee = new Employee();
            int isExsit = financeServ.checkFinanceImportNoandYearMonthIsExsit(empHours);
            if (isExsit == 0) {
                employee = personServ.getEmployeeByEmpno(empHours.getEmpNo());
            }
            employee.setType(isExsit);
            employees.add(employee);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employees);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkEmpNoandYearMonthIsExsit", method = RequestMethod.POST)
    public void checkEmpNoandYearMonthIsExsit(EmpHours empHours, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<Employee> employees = new ArrayList<Employee>();
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Employee employee = new Employee();
            int isExsit = financeServ.checkEmpNoandYearMonthIsExsit(empHours);
            if (isExsit == 0) {
                employee = personServ.getEmployeeByEmpno(empHours.getEmpNo());
            }
            employee.setType(isExsit);
            employees.add(employee);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employees);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toaddpersonsalarypageappli", method = RequestMethod.GET)
    public ModelAndView toaddpersonsalarypageappli(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("addfinanceappli");
        Employee employee = new Employee();
        List<Employee> empList = personServ.findAllEmployeeAll();
        view.addObject("employee", employee);
        view.addObject("empList", empList);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/toupdateEmployeeSalaryByempNo", method = RequestMethod.GET)
    public ModelAndView toupdateEmployeeSalaryByempNo(String empNo, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("updatefinance");
        Employee employee = personServ.getEmployeeByEmpno(empNo);
        view.addObject("employee", employee);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/toaddfinanceimportdata", method = RequestMethod.GET)
    public ModelAndView toaddfinanceimportdata(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("addfinanceimportdata");
        List<Employee> empList = personServ.findAllEmployeeAll();
        view.addObject("empList", empList);
        view.addObject("financeImportData", new FinanceImportData());
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/toaddpersonHourspage", method = RequestMethod.GET)
    public ModelAndView toaddpersonHourspage(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("addemphours");
        List<Employee> empList = personServ.findAllEmployeeAll();
        view.addObject("empList", empList);
        view.addObject("empHours", new EmpHours());
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/toSetUp", method = RequestMethod.GET)
    public ModelAndView toSetUp(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("financesetup");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.addObject("userInfo", userInfo);
        FinanceSetUpData fsu = financeServ.findFinanceSetUpData();
        if(fsu==null)
            fsu = new FinanceSetUpData();
        view.addObject("fsu", fsu);
        view.addObject("flag", 0);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/updateEmpHoursToMysql", method = RequestMethod.POST)
    public ModelAndView updateEmpHoursToMysql(EmpHours empHours, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("emphours");
        financeServ.updateEmpHoursByBean(empHours);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<EmpHours> empHoursList = financeServ.findAllEmpHours(new Employee());
        int recordCount = financeServ.findAllEmpHoursHours();
        Employee employee = new Employee();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("empHoursList", empHoursList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 3);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addEmpHoursToMysql", method = RequestMethod.POST)
    public ModelAndView addEmpHoursToMysql(EmpHours empHours, HttpSession session) throws Exception {
        Employee em = personServ.getEmployeeByEmpno(empHours.getEmpNo());
        empHours.setName(em.getName());
        ModelAndView view = new ModelAndView("emphours");
        financeServ.addEmpHoursByBean(empHours);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<EmpHours> empHoursList = financeServ.findAllEmpHours(new Employee());
        int recordCount = financeServ.findAllEmpHoursHours();
        Employee employee = new Employee();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("empHoursList", empHoursList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 1);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/updateFinanceImportDataToMysql", method = RequestMethod.POST)
    public ModelAndView updateFinanceImportDataToMysql(FinanceImportData financeImportData, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("financeimportdata");
        financeServ.updateFinanceImportDataByBean(financeImportData);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<FinanceImportData> financeImportDataList = financeServ.findAllFinanceImportData(new Employee());
        int recordCount = financeServ.findAllFinanceImportDataCount();
        Employee employee = new Employee();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("financeImportDataList", financeImportDataList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 3);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addFinanceImportDataToMysql", method = RequestMethod.POST)
    public ModelAndView addFinanceImportDataToMysql(FinanceImportData financeImportData, HttpSession session) throws Exception {
        Employee em = personServ.getEmployeeByEmpno(financeImportData.getEmpNo());
        financeImportData.setName(em.getName());
        ModelAndView view = new ModelAndView("financeimportdata");
        financeServ.addFinanceImportDataByBean(financeImportData);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<FinanceImportData> financeImportDataList = financeServ.findAllFinanceImportData(new Employee());
        int recordCount = financeServ.findAllFinanceImportDataCount();
        Employee employee = new Employee();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("financeImportDataList", financeImportDataList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 1);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/toupdateEmployeeHoursByempNo", method = RequestMethod.GET)
    public ModelAndView toupdateEmployeeHoursByempNo(Integer id, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("updateemphours");
        EmpHours empHours = financeServ.getEmpHoursByEmpNo(id);
        view.addObject("empHours", empHours);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/toupdateFinanceImportDataById", method = RequestMethod.GET)
    public ModelAndView toupdateFinanceImportDataById(Integer id, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("updatefinanceimportdata");
        FinanceImportData financeImportData = financeServ.getFinanceImportDataById(id);
        view.addObject("financeImportData", financeImportData);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/toupdateEmployeeSalaryByempNoAppli", method = RequestMethod.GET)
    public ModelAndView toupdateEmployeeSalaryByempNoAppli(String empNo, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("updatefinanceappli");
        Employee employee = personServ.getEmployeeByEmpno(empNo);
        view.addObject("employee", employee);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmpByBatch", method = RequestMethod.GET)
    public ModelAndView deleteEmpByBatch(Employee employee, HttpSession session) throws Exception {
        financeServ.deleteEmpSalaryByBatch(employee);
        ModelAndView view = new ModelAndView("finance");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(new Employee());
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 2);
        return view;
    }

@ResponseBody
@RequestMapping(value = "/deleteFinanceImportDataByBatch", method = RequestMethod.GET)
public ModelAndView deleteFinanceImportDataByBatch(Employee employee, HttpSession session) throws Exception {
    financeServ.deleteFinanceImportDataByBatch(employee);
    ModelAndView view = new ModelAndView("emphours");
    UserInfo userInfo = (UserInfo) session.getAttribute("account");
    List<Position> positionList = personServ.findAllPositionAll();
    List<Dept> deptList = personServ.findAllDeptAll();
    List<Employee> empList = personServ.findAllEmployeeAll();
    List<FinanceImportData> financeImportDataList = financeServ.findAllFinanceImportData(employee);
    int recordCount = financeServ.findAllFinanceImportDataCount();
    int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
    employee.setMaxPage(maxPage);
    employee.setRecordCount(recordCount);
    view.addObject("financeImportDataList", financeImportDataList);
    view.addObject("empList", empList);
    view.addObject("employee", employee);
    view.addObject("positionList", positionList);
    view.addObject("deptList", deptList);
    view.addObject("userInfo", userInfo);
    view.addObject("flag", 2);
    return view;
}


    @ResponseBody
    @RequestMapping(value = "/deleteEmpHoursByBatch", method = RequestMethod.GET)
    public ModelAndView deleteEmpHoursByBatch(Employee employee, HttpSession session) throws Exception {
        financeServ.deleteEmpHoursByBatch(employee);
        ModelAndView view = new ModelAndView("emphours");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<EmpHours> empHoursList = financeServ.findAllEmpHours(new Employee());
        int recordCount = financeServ.findAllEmpHoursHours();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("empHoursList", empHoursList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 2);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmpByBatchAppli", method = RequestMethod.GET)
    public ModelAndView deleteEmpByBatchAppli(Employee employee, HttpSession session) throws Exception {
        financeServ.deleteEmpSalaryByBatch(employee);
        ModelAndView view = new ModelAndView("financeappli");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(new Employee());
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 2);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addsalary", method = RequestMethod.POST)
    public ModelAndView addsalary(Employee employee, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("finance");
        financeServ.addSalaryByBean(employee);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(new Employee());
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addsalaryappli", method = RequestMethod.POST)
    public ModelAndView addsalaryappli(Employee employee, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("financeappli");
        employee.setState(0);
        financeServ.addSalaryByBean(employee);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(new Employee());
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteFinanceImportData", method = RequestMethod.GET)
    public ModelAndView deleteFinanceImportData(Integer id, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("financeimportdata");
            financeServ.deleteFinanceImportDataById(id);
            Employee employee = new Employee();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<FinanceImportData> financeImportDataList = financeServ.findAllFinanceImportData(employee);
            int recordCount = financeServ.findAllFinanceImportDataCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("financeImportDataList", financeImportDataList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmployeeSalaryByEmpNo", method = RequestMethod.GET)
    public ModelAndView deleteEmployeeSalaryByEmpNo(String empNo, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("finance");
            personServ.deleteEmployeeSalaryByEmpno(empNo);
            Employee employee = new Employee();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> employeeList = personServ.findAllEmployeeFinance(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmployeeHoursByEmpNo", method = RequestMethod.GET)
    public ModelAndView deleteEmployeeHoursByEmpNo(Integer id, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("emphours");
            financeServ.deleteEmployeeHoursByEmpno(id);
            Employee employee = new Employee();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<EmpHours> empHoursList = financeServ.findAllEmpHours(employee);
            int recordCount = financeServ.findAllEmpHoursHours();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("empHoursList", empHoursList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeSalaryByCondition", method = RequestMethod.POST)
    public void queryEmployeeSalaryByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryEmployeeSalaryByCondition(employee);
            int recordCount = personServ.queryEmployeeSalaryByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveFinanceSetUp", method = RequestMethod.POST)
    public void saveFinanceSetUp(FinanceSetUpData financeSetUpData, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            financeServ.saveFinanceSetUp(financeSetUpData);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(1);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeSalaryByConditionAppli", method = RequestMethod.POST)
    public void queryEmployeeSalaryByConditionAppli(Employee employee, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryEmployeeSalaryByCondition(employee);
            int recordCount = personServ.queryEmployeeSalaryByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tofinance")
    public ModelAndView tofinance(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("finance");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toFinanceImportData")
    public ModelAndView toFinanceImportData(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("financeimportdata");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<FinanceImportData> financeImportDataList = financeServ.findAllFinanceImportData(employee);
        int recordCount = financeServ.findAllFinanceImportDataCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("financeImportDataList", financeImportDataList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }


    @ResponseBody
    @RequestMapping("/toAfterSalary")
    public ModelAndView toAfterSalary(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("aftersalary");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<SalaryDataOutPut> salaryDataOutPutList = financeServ.findAllSalaryDataOutPut(employee);
        int recordCount = financeServ.findAllSalaryDataOutPutCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("salaryDataOutPutList", salaryDataOutPutList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }


    @ResponseBody
    @RequestMapping("/toEmpHours")
    public ModelAndView toEmpHours(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("emphours");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<EmpHours> empHoursList = financeServ.findAllEmpHours(employee);
        int recordCount = financeServ.findAllEmpHoursHours();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("empHoursList", empHoursList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/querySalaryDataOutPutByCondition", method = RequestMethod.POST)
    public void querySalaryDataOutPutByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<SalaryDataOutPut> salaryDataOutPutList = financeServ.querySalaryDataOutPutByCondition(employee);
            int recordCount = financeServ.querySalaryDataOutPutByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (salaryDataOutPutList.size() > 0) {
                salaryDataOutPutList.get(0).setMaxPage(maxPage);
                salaryDataOutPutList.get(0).setRecordCount(recordCount);
                salaryDataOutPutList.get(0).setCurrentPage(employee.getCurrentPage());
                salaryDataOutPutList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(salaryDataOutPutList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryEmployeeHoursByCondition", method = RequestMethod.POST)
    public void queryEmployeeHoursByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<EmpHours> empHoursList = financeServ.queryEmployeeHoursByCondition(employee);
            int recordCount = financeServ.queryEmployeeHoursByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (empHoursList.size() > 0) {
                empHoursList.get(0).setMaxPage(maxPage);
                empHoursList.get(0).setRecordCount(recordCount);
                empHoursList.get(0).setCurrentPage(employee.getCurrentPage());
                empHoursList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(empHoursList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }



    @ResponseBody
    @RequestMapping(value = "/queryFinanceImportDataByCondition", method = RequestMethod.POST)
    public void queryFinanceImportDataByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<FinanceImportData> financeImportDataList = financeServ.queryFinanceImportDataByCondition(employee);
            int recordCount = financeServ.queryFinanceImportDataByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (financeImportDataList.size() > 0) {
                financeImportDataList.get(0).setMaxPage(maxPage);
                financeImportDataList.get(0).setRecordCount(recordCount);
                financeImportDataList.get(0).setCurrentPage(employee.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(financeImportDataList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/dataInMysql", method = RequestMethod.POST)
    public ModelAndView dataInMysql(@RequestParam("file1") MultipartFile file1, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("salaryimport");
        String errorMessage;
        try {
            List<Salary> salaryList = financeServ.translateExcelToBean(file1);
            financeServ.saveAllSalaryData(salaryList);
            view.addObject("financeImportData", new FinanceImportData());
            view.addObject("empHours", new EmpHours());
            view.addObject("flag", 1);
            return view;
        } catch (NumberFormatException e) {
            view.addObject("financeImportData", new FinanceImportData());
            view.addObject("empHours", new EmpHours());
            view.addObject("flag", 2);
            view.addObject("empnoerror", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return view;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/dataInMysqlPerson", method = RequestMethod.POST)
    public ModelAndView dataInMysqlPerson(@RequestParam("file2") MultipartFile file2, EmpHours empHours, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("salaryimport");
        String errorMessage;
        try {
            List<EmpHours> empHoursList = financeServ.translateExcelToBeanEmpHours(file2, empHours.getYearMonthStr());
            financeServ.saveAllEmpHours(empHoursList, empHours.getYearMonthStr());
            view.addObject("flag2", 1);
            view.addObject("flag3", 0);
            view.addObject("empnoerror3", empHours.getMedicalInsuran());
            view.addObject("financeImportData", new FinanceImportData());
            view.addObject("empHours", new EmpHours());
            return view;
        } catch (NumberFormatException e) {
            view.addObject("flag2", 2);
            view.addObject("empnoerror2", e.getMessage());
            view.addObject("empnoerror3", "");
            view.addObject("financeImportData", new FinanceImportData());
            view.addObject("flag3", 0);
            view.addObject("empnoerror3", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return view;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/dataInMysqlFinance", method = RequestMethod.POST)
    public ModelAndView dataInMysqlFinance(@RequestParam("file3") MultipartFile file3, FinanceImportData financeImportData, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("salaryimport");
        String errorMessage;
        try {
            List<FinanceImportData> financeImportDataList = financeServ.translateExcelToBeanFinanceImportData(file3, financeImportData.getYearMonth());
            financeServ.saveAllFinanceImportData(financeImportDataList, financeImportData.getYearMonth());
            view.addObject("flag3", 1);
            view.addObject("empHours", new EmpHours());
            view.addObject("financeImportData", new FinanceImportData());
            return view;
        } catch (NumberFormatException e) {
            view.addObject("empHours", new EmpHours());
            view.addObject("financeImportData", new FinanceImportData());
            view.addObject("flag3", 2);
            view.addObject("empnoerror3", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return view;
        }
    }


}
