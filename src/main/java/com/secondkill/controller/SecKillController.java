package com.secondkill.controller;

import com.secondkill.dto.Exposed;
import com.secondkill.entity.SecKill;
import com.secondkill.enums.SecKillStatusEnum;
import com.secondkill.exceptions.IllegalSecKillException;
import com.secondkill.exceptions.RepeatSecKillException;
import com.secondkill.exceptions.SecKillException;
import com.secondkill.exceptions.StockLackException;
import com.secondkill.service.SecKillService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody
    public String execute(@RequestParam int secKillId, @RequestParam String userPhone, String md5) {
        JSONObject json = new JSONObject();
        try {
            secKillService.execute(secKillId, userPhone, md5);
            json.put("status", SecKillStatusEnum.SUCCESS.getStatus());
            json.put("info", SecKillStatusEnum.SUCCESS.getInfo());
        } catch (RepeatSecKillException e) {
            json.put("status", SecKillStatusEnum.REPEAT.getStatus());
            json.put("info", SecKillStatusEnum.REPEAT.getInfo());
            System.err.println(userPhone + e.getMessage());
        } catch (IllegalSecKillException e) {
            json.put("status", SecKillStatusEnum.ILLEGAL.getStatus());
            json.put("info", SecKillStatusEnum.ILLEGAL.getInfo());
            System.err.println(userPhone + e.getMessage());
        } catch (StockLackException e) {
            json.put("status", SecKillStatusEnum.STOCK_LACKING.getStatus());
            json.put("info", SecKillStatusEnum.STOCK_LACKING.getInfo());
            System.err.println(userPhone + e.getMessage());
        } catch (SecKillException e) {
            json.put("status", SecKillStatusEnum.EXCEPTION.getStatus());
            json.put("info", SecKillStatusEnum.EXCEPTION.getInfo());
            System.err.println(userPhone + e.getMessage());
        }
        return json.toString();
    }

    @RequestMapping(value = "/exposed/{secKillId}")
    @ResponseBody
    public String exposed(@PathVariable int secKillId) {
        Exposed exposed = secKillService.exposed(secKillId);
        return JSONObject.fromObject(exposed).toString();
    }

    @RequestMapping(value = "list")
    public String list(ModelMap map) {
        List<SecKill> secs = secKillService.getAll();
        map.addAttribute("secs", secs);
        return "list";
    }

    @RequestMapping(value = "get/{secKillId}")
    public String getOne(@PathVariable int secKillId, ModelMap map) {
        SecKill secKill = secKillService.get(secKillId);
        map.addAttribute("secKill", secKill);
        return "detail";
    }

    @RequestMapping(value = "/getTime", method = RequestMethod.GET)
    @ResponseBody
    public String getTime() {
        Long now = new Date().getTime();
        return now.toString();
    }
}
