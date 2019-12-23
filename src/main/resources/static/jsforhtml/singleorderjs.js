function trimstr(str) { //删除左右两端的空格
    if (str != null) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    return "";
}

function submitartdealdatafail(alertip, submitflag) {
    alert(alertip);

}

function submitartdealdata() {
    var submitflag = true;
    var alerttip = "";
    var flag = false;
    var showmessage = "";
    var tipmessage = "颜色值等信息补充";
    //
    var needrefersamplestr = document.getElementById("needrefersample");//复选框

    if (needrefersamplestr.checked == true) {
        flag = true;
        showmessage += needrefersamplestr.value + ",";
    }
    var uvprintstr = document.getElementById("uvprint");
    var uvprintplusmessagestr = $("#uvprintplusmessage").val();
    if (uvprintstr.checked == true) {
        flag = true;
        showmessage += uvprintstr.value + ",";
        if (tipmessage == uvprintplusmessagestr || trimstr(uvprintplusmessagestr).length <= 0) {
            alerttip = "请为UV选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(uvprintplusmessagestr) + ",";
    }

    var polishingstr = document.getElementById("polishing");//复选框
    var sandblastingstr = document.getElementById("sandblasting");//复选框
    var doruststr = document.getElementById("dorust");//复选框
    var silkprintstr = document.getElementById("silkprint");//复选框
    var silkprintplusmesstr = $("#silkprintplusmes").val();
    if (polishingstr.checked == true) {
        flag = true;
        showmessage += polishingstr.value + ",";
    }
    if (sandblastingstr.checked == true) {
        flag = true;
        showmessage += sandblastingstr.value + ",";
    }
    if (doruststr.checked == true) {
        flag = true;
        showmessage += doruststr.value + ",";
    }

    if (silkprintstr.checked == true) {
        flag = true;
        showmessage += silkprintstr.value + ":";
        if (tipmessage == silkprintplusmesstr || trimstr(silkprintplusmesstr).length <= 0) {
            alerttip = "请为丝印打印选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(silkprintplusmesstr) + ",";
    }

    var wiredrawlinesstr = $('#wiredrawlines option:selected').val();
    var wiredrawingstr = document.getElementById("wiredrawing");//复选框
    if (wiredrawingstr.checked == true) {
        flag = true;
        showmessage += wiredrawingstr.value + ":";
        if (wiredrawlinesstr == "拉丝纹路" || wiredrawlinesstr == 0) {
            alerttip = "请为拉丝选择纹路."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += wiredrawlinesstr + ",";
    }

    var watertranprintplusmesstr = $("#watertranprintplusmes").val();
    var watertranprintstr = document.getElementById("watertranprint");//复选框
    if (watertranprintstr.checked == true) {
        flag = true;
        showmessage += watertranprintstr.value + ":";
        if (tipmessage == watertranprintplusmesstr || trimstr(watertranprintplusmesstr).length <= 0) {
            alerttip = "请为水转印选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(watertranprintplusmesstr) + ",";
    }

    var bakingpaintplusmesstr = $("#bakingpaintplusmes").val();
    var bakingpaintstr = document.getElementById("bakingpaint");//复选框
    if (bakingpaintstr.checked == true) {
        flag = true;
        showmessage += bakingpaintstr.value + ":";
        if (tipmessage == bakingpaintplusmesstr || trimstr(bakingpaintplusmesstr).length <= 0) {
            alerttip = "请为烤漆选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(bakingpaintplusmesstr) + ",";
    }

    var hottransprintplusmesstr = $("#hottransprintplusmes").val();
    var hottransprintstr = document.getElementById("hottransprint");//复选框
    if (hottransprintstr.checked == true) {
        flag = true;
        showmessage += hottransprintstr.value + ":";
        if (tipmessage == hottransprintplusmesstr || trimstr(hottransprintplusmesstr).length <= 0) {
            alerttip = "请为热转印选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(hottransprintplusmesstr) + ",";
    }

    var dustplusmessagestr = $("#dustplusmessage").val();
    var dustingstr = document.getElementById("dusting");//复选框
    if (dustingstr.checked == true) {
        flag = true;
        showmessage += dustingstr.value + ":";
        if (tipmessage == dustplusmessagestr || trimstr(dustplusmessagestr).length <= 0) {
            alerttip = "请为喷粉选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(dustplusmessagestr) + ",";
    }

    var patchplusmesstr = $("#patchplusmes").val();
    var patchstr = document.getElementById("patch");//复选框
    if (patchstr.checked == true) {
        flag = true;
        showmessage += patchstr.value + ":";
        if (tipmessage == patchplusmesstr || trimstr(patchplusmesstr).length <= 0) {
            alerttip = "请为贴膜选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(patchplusmesstr) + ",";
    }

    var elecplateplusmesstr = $("#elecplateplusmes").val();
    var elecplatestr = document.getElementById("elecplate");//复选框
    if (elecplatestr.checked == true) {
        flag = true;
        showmessage += elecplatestr.value + ":";
        if (tipmessage == elecplateplusmesstr || trimstr(elecplateplusmesstr).length <= 0) {
            alerttip = "请为电镀选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(elecplateplusmesstr) + ",";
    }

    var corrosionplusmesstr = $("#corrosionplusmes").val();
    var corrosionstr = document.getElementById("corrosion");//复选框
    if (corrosionstr.checked == true) {
        flag = true;
        showmessage += corrosionstr.value + ":";
        if (tipmessage == corrosionplusmesstr || trimstr(corrosionplusmesstr).length <= 0) {
            alerttip = "请为腐蚀选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(corrosionplusmesstr) + ",";
    }

    var waterplatingplusmesstr = $("#waterplatingplusmes").val();
    var waterplatingstr = document.getElementById("waterplating");//复选框
    if (waterplatingstr.checked == true) {
        flag = true;
        showmessage += waterplatingstr.value + ":";
        if (tipmessage == waterplatingplusmesstr || trimstr(waterplatingplusmesstr).length <= 0) {
            alerttip = "请为水镀选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(waterplatingplusmesstr) + ",";
    }

    var superglueplusmesstr = $("#superglueplusmes").val();
    var supergluestr = document.getElementById("superglue");//复选框
    if (supergluestr.checked == true) {
        flag = true;
        showmessage += supergluestr.value + ":";
        if (tipmessage == superglueplusmesstr || trimstr(superglueplusmesstr).length <= 0) {
            alerttip = "请为灌超级胶选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(superglueplusmesstr) + ",";
    }

    var anodicoxidplusmesstr = $("#anodicoxidplusmes").val();
    var anodicoxidstr = document.getElementById("anodicoxid");//复选框
    if (anodicoxidstr.checked == true) {
        flag = true;
        showmessage += anodicoxidstr.value + ":";
        if (tipmessage == anodicoxidplusmesstr || trimstr(anodicoxidplusmesstr).length <= 0) {
            alerttip = "请为阳极氧化选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(anodicoxidplusmesstr) + ",";
    }

    var resinplusmesstr = $("#resinplusmes").val();
    var resinstr = document.getElementById("resin");//复选框
    if (resinstr.checked == true) {
        flag = true;
        showmessage += resinstr.value + ":";
        if (tipmessage == resinplusmesstr || trimstr(resinplusmesstr).length <= 0) {
            alerttip = "请为灌树脂选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(resinplusmesstr) + ",";
    }

    var paintfillplusmesstr = $("#paintfillplusmes").val();
    var paintfillstr = document.getElementById("paintfill");//复选框
    if (paintfillstr.checked == true) {
        flag = true;
        showmessage += paintfillstr.value + ":";
        if (tipmessage == paintfillplusmesstr || trimstr(paintfillplusmesstr).length <= 0) {
            alerttip = "请为填漆选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(paintfillplusmesstr) + ",";
    }

    var coverprotemethodplumesstr = $("#coverprotemethodplumes").val();
    var coverprotemethodstr = document.getElementById("coverprotemethod");//复选框
    if (coverprotemethodstr.checked == true) {
        flag = true;
        showmessage += coverprotemethodstr.value + ":";
        if (tipmessage == coverprotemethodplumesstr || trimstr(coverprotemethodplumesstr).length <= 0 || coverprotemethodplumesstr == 0) {
            alerttip = "请为表面保护方式选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(coverprotemethodplumesstr) + ",";
    }

    var otherartsstr = $("#otherarts").val();
    if ("其它工艺" != otherartsstr && trimstr(otherartsstr).length > 0) {
        flag = true;
        showmessage += "其它工艺:" + trimstr(otherartsstr) + "";
    }
    if (trimstr(showmessage).length <= 0) {
        showmessage = "无";
    }
    if (submitflag) {
        document.getElementById("coverbehidepagetwo").style.display = "none";
        document.getElementById("smallshowdivforartdeal").style.display = "none";
        var tipsforwherestr = $("#tipsforwhere").val();
        $("#" + tipsforwherestr).val(showmessage);

    } else {
        alert(alerttip);
    }

}

function ifchoseneedrefersample() {

    var isTureOrFalse = document.getElementById("needrefersample").checked;
    if (isTureOrFalse) {
        $('#bakingpaintplusmes').val("")
        $('#bakingpaintplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#dustplusmessage').val("")
        $('#dustplusmessage').attr('placeholder', "颜色值等信息补充...");
        $('#elecplateplusmes').val("")
        $('#elecplateplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#waterplatingplusmes').val("")
        $('#waterplatingplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#anodicoxidplusmes').val("")
        $('#anodicoxidplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#paintfillplusmes').val("")
        $('#paintfillplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#uvprintplusmessage').val("")
        $('#uvprintplusmessage').attr('placeholder', "颜色值等信息补充...");
        $('#silkprintplusmes').val("")
        $('#silkprintplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#watertranprintplusmes').val("")
        $('#watertranprintplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#hottransprintplusmes').val("")
        $('#hottransprintplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#patchplusmes').val("")
        $('#patchplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#superglueplusmes').val("")
        $('#superglueplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#corrosionplusmes').val("")
        $('#corrosionplusmes').attr('placeholder', "颜色值等信息补充...");
        $('#resinplusmes').val("")
        $('#resinplusmes').attr('placeholder', "颜色值等信息补充...");
        document.getElementById("polishing").checked = false;
        document.getElementById("sandblasting").checked = false;
        document.getElementById("dorust").checked = false;
        document.getElementById("wiredrawing").checked = false;
        document.getElementById("bakingpaint").checked = false;
        document.getElementById("dusting").checked = false;
        document.getElementById("elecplate").checked = false;
        document.getElementById("waterplating").checked = false;
        document.getElementById("anodicoxid").checked = false;
        document.getElementById("paintfill").checked = false;
        document.getElementById("uvprint").checked = false;
        document.getElementById("silkprint").checked = false;
        document.getElementById("watertranprint").checked = false;
        document.getElementById("hottransprint").checked = false;
        document.getElementById("patch").checked = false;
        document.getElementById("corrosion").checked = false;
        document.getElementById("superglue").checked = false;
        document.getElementById("resin").checked = false;
        document.getElementById("coverprotemethod").checked = false;
        $('#otherarts').val("")
        $('#otherarts').attr('placeholder', "其它工艺...");
        $('#polishing').attr("disabled", true);
        $('#sandblasting').attr("disabled", true);
        $('#dorust').attr("disabled", true);
        $('#wiredrawing').attr("disabled", true);
        $('#wiredrawlines').attr("disabled", true);
        $('#bakingpaint').attr("disabled", true);
        $('#bakingpaintplusmes').attr("disabled", true);
        $('#dusting').attr("disabled", true);
        $('#dustplusmessage').attr("disabled", true);
        $('#elecplate').attr("disabled", true);
        $('#elecplateplusmes').attr("disabled", true);
        $('#waterplating').attr("disabled", true);
        $('#waterplatingplusmes').attr("disabled", true);
        $('#anodicoxid').attr("disabled", true);
        $('#anodicoxidplusmes').attr("disabled", true);
        $('#paintfill').attr("disabled", true);
        $('#paintfillplusmes').attr("disabled", true);
        $('#uvprint').attr("disabled", true);
        $('#uvprintplusmessage').attr("disabled", true);
        $('#silkprint').attr("disabled", true);
        $('#silkprintplusmes').attr("disabled", true);
        $('#watertranprint').attr("disabled", true);
        $('#watertranprintplusmes').attr("disabled", true);
        $('#hottransprint').attr("disabled", true);
        $('#hottransprintplusmes').attr("disabled", true);
        $('#patch').attr("disabled", true);
        $('#patchplusmes').attr("disabled", true);
        $('#corrosion').attr("disabled", true);
        $('#corrosionplusmes').attr("disabled", true);
        $('#superglue').attr("disabled", true);
        $('#superglueplusmes').attr("disabled", true);
        $('#resin').attr("disabled", true);
        $('#resinplusmes').attr("disabled", true);
        $('#coverprotemethod').attr("disabled", true);
        $('#coverprotemethodplumes').attr("disabled", true);
        $('#otherarts').attr("disabled", true);

    } else {
        $('#polishing').attr("disabled", false);
        $('#sandblasting').attr("disabled", false);
        $('#dorust').attr("disabled", false);
        $('#wiredrawing').attr("disabled", false);
        $('#wiredrawlines').attr("disabled", false);
        $('#bakingpaint').attr("disabled", false);
        $('#bakingpaintplusmes').attr("disabled", false);
        $('#dusting').attr("disabled", false);
        $('#dustplusmessage').attr("disabled", false);
        $('#elecplate').attr("disabled", false);
        $('#elecplateplusmes').attr("disabled", false);
        $('#waterplating').attr("disabled", false);
        $('#waterplatingplusmes').attr("disabled", false);
        $('#anodicoxid').attr("disabled", false);
        $('#anodicoxidplusmes').attr("disabled", false);
        $('#paintfill').attr("disabled", false);
        $('#paintfillplusmes').attr("disabled", false);
        $('#uvprint').attr("disabled", false);
        $('#uvprintplusmessage').attr("disabled", false);
        $('#silkprint').attr("disabled", false);
        $('#silkprintplusmes').attr("disabled", false);
        $('#watertranprint').attr("disabled", false);
        $('#watertranprintplusmes').attr("disabled", false);
        $('#hottransprint').attr("disabled", false);
        $('#hottransprintplusmes').attr("disabled", false);
        $('#patch').attr("disabled", false);
        $('#patchplusmes').attr("disabled", false);
        $('#corrosion').attr("disabled", false);
        $('#corrosionplusmes').attr("disabled", false);
        $('#superglue').attr("disabled", false);
        $('#superglueplusmes').attr("disabled", false);
        $('#resin').attr("disabled", false);
        $('#resinplusmes').attr("disabled", false);
        $('#coverprotemethod').attr("disabled", false);
        $('#coverprotemethodplumes').attr("disabled", false);
        $('#otherarts').attr("disabled", false);
    }
}


function closesmallshowdivforartdeal() {
    document.getElementById("coverbehidepagetwo").style.display = "none";
    document.getElementById("smallshowdivforartdeal").style.display = "none";
}


function showstrakediv() {
    var flag = true;
    var checkArry = document.getElementById("strake");
    var materialattr = $("#materialattr6").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattr == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("strake" + "art");
    }
}

function showaluminumplatediv() {
    var flag = true;
    var checkArry = document.getElementById("aluminumplate");
    var thickness = $("#thickness9").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("aluminumplate" + "art");
    }
}

function showelectplatediv() {
    var flag = true;
    var checkArry = document.getElementById("electplate");
    var thickness = $("#thickness8").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("electplate" + "art");
    }
}

function showgalvanizedsheetdiv() {
    var flag = true;
    var checkArry = document.getElementById("galvanizedsheet");
    var thickness = $("#thickness7").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("galvanizedsheet" + "art");
    }
}

function showcopperplatediv() {
    var flag = true;
    var checkArry = document.getElementById("copperplate");
    var materialattribute = $("#materialattr15").val();
    var thickness = $("#thickness6").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("copperplate" + "art");
    }
}

function showironplatediv() {
    var flag = true;
    var checkArry = document.getElementById("ironplate");
    var materialattribute = $("#materialattr5").val();
    var thickness = $("#thickness5").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("ironplate" + "art");
    }
}

function showaluminumalloyplatediv() {
    var flag = true;
    var checkArry = document.getElementById("aluminumalloyplate");
    var materialattribute = $("#marialattr3").val();
    var thickness = $("#thickness3").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("aluminumalloyplate" + "art");
    }
}


function showacrylicdiv() {
    var flag = true;
    var checkArry = document.getElementById("acrylic");
    var surattribute = $("#thick01").val();
    var materialattribute = $("#colorattr").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("acrylic" + "art");
    }
}


function showpcplatediv() {
    var flag = true;
    var checkArry = document.getElementById("pcplate");
    var surattribute = $("#thickness02").val();
    var materialattribute = $("#colorattr02").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("pcplate" + "art");
    }
}

function showmarblesalbdiv() {
    var flag = true;
    var checkArry = document.getElementById("marblesalb");
    var surattribute = $("#thickness03").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("marblesalb" + "art");
    }
}

function showalucomppaneldiv() {
    var flag = true;
    var checkArry = document.getElementById("alucomppanel");
    var surattribute = $("#thickness05").val();
    var materialattribute = $("#colorattr05").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("alucomppanel" + "art");
    }
}

function showpvcplatediv() {
    var flag = true;
    var checkArry = document.getElementById("pvcplate");
    var surattribute = $("#thickness06").val();
    var materialattribute = $("#colorattr06").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("pvcplate" + "art");
    }
}

function showmoodplatediv() {
    var flag = true;
    var checkArry = document.getElementById("moodplate");
    var surattribute = $("#thickness07").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("moodplate" + "art");
    }
}

function showfangtongdiv() {
    var flag = true;
    var checkArry = document.getElementById("fangtong");
    var surattribute = $("#materiattr").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("fangtong" + "art");
    }
}

function showbiantongdiv() {
    var flag = true;
    var checkArry = document.getElementById("biantong");
    var surattribute = $("#biantongthicness").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("biantong" + "art");
    }
}

function showyuanguandiv() {
    var flag = true;
    var checkArry = document.getElementById("yuanguan");
    var surattribute = $("#yuanguanattri").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("yuanguan" + "art");
    }
}

function showjiaotiaodiv() {
    var flag = true;
    var checkArry = document.getElementById("jiaotiao");
    var surattribute = $("#jiaotiaoattri").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("jiaotiao" + "art");
    }
}

function showyuanzhidiv() {
    var flag = true;
    var checkArry = document.getElementById("yuanzhi");
    var surattribute = $("#yuanzhithicness").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("yuanzhi" + "art");
    }
}

function showxingcaidiv() {
    var flag = true;
    var checkArry = document.getElementById("xingcai");
    var surattribute = $("#xingcaithickness").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("xingcai" + "art");
    }
}


function showstainlessdiv() {
    var flag2 = true;
    var checkArry2 = document.getElementById("stainless2");
    var surattribute2 = $("#coverattri2").val();
    var materialattribute2 = $("#materialattri2").val();
    var thickness2 = $("#thickness2").val();

    if (checkArry2.checked != true) {
        flag2 = false;
    }
    if (surattribute2 == null) {

        flag2 = false;
    }
    if (materialattribute2 == null) {
        flag2 = false;
    }
    if (thickness2 == null) {
        flag2 = false;
    }
    if (flag2) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("stainless2" + "art");
    }
}

function showlastdiv() {
    var flag = true;
    var checkArry = document.getElementById("stainlesteel");
    var surattribute = $("#surattribute").val();
    var materialattribute = $("#materialattribute").val();
    var thickness = $("#thickness").val();
    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("stainlesteel" + "art");
    }

}

function clearsmallartsfordealdivdata() {
    $('#polishing').attr("disabled", false);
    $('#sandblasting').attr("disabled", false);
    $('#dorust').attr("disabled", false);
    $('#wiredrawing').attr("disabled", false);
    $('#wiredrawlines').attr("disabled", false);
    $('#bakingpaint').attr("disabled", false);
    $('#bakingpaintplusmes').attr("disabled", false);
    $('#dusting').attr("disabled", false);
    $('#dustplusmessage').attr("disabled", false);
    $('#elecplate').attr("disabled", false);
    $('#elecplateplusmes').attr("disabled", false);
    $('#waterplating').attr("disabled", false);
    $('#waterplatingplusmes').attr("disabled", false);
    $('#anodicoxid').attr("disabled", false);
    $('#anodicoxidplusmes').attr("disabled", false);
    $('#paintfill').attr("disabled", false);
    $('#paintfillplusmes').attr("disabled", false);
    $('#uvprint').attr("disabled", false);
    $('#uvprintplusmessage').attr("disabled", false);
    $('#silkprint').attr("disabled", false);
    $('#silkprintplusmes').attr("disabled", false);
    $('#watertranprint').attr("disabled", false);
    $('#watertranprintplusmes').attr("disabled", false);
    $('#hottransprint').attr("disabled", false);
    $('#hottransprintplusmes').attr("disabled", false);
    $('#patch').attr("disabled", false);
    $('#patchplusmes').attr("disabled", false);
    $('#corrosion').attr("disabled", false);
    $('#corrosionplusmes').attr("disabled", false);
    $('#superglue').attr("disabled", false);
    $('#superglueplusmes').attr("disabled", false);
    $('#resin').attr("disabled", false);
    $('#resinplusmes').attr("disabled", false);
    $('#coverprotemethod').attr("disabled", false);
    $('#coverprotemethodplumes').attr("disabled", false);
    $('#otherarts').attr("disabled", false);
    if ($('#needrefersample').is(":checked")) {
        $("#needrefersample").prop("checked", false);
    }
    if ($('#uvprint').is(":checked")) {
        $("#uvprint").prop("checked", false);
    }
    if ($('#sandblasting').is(":checked")) {
        $("#sandblasting").prop("checked", false);
    }
    if ($('#dorust').is(":checked")) {
        $("#dorust").prop("checked", false);
    }
    if ($('#silkprint').is(":checked")) {
        $("#silkprint").prop("checked", false);
    }

    if ($('#polishing').is(":checked")) {
        $("#polishing").prop("checked", false);
    }
    if ($('#wiredrawing').is(":checked")) {
        $("#wiredrawing").prop("checked", false);
    }
    $("#wiredrawlines").val(0);
    $("#coverprotemethodplumes").val(0);
    $('#patchplusmes').val("")
    $('#uvprintplusmessage').attr('placeholder', "颜色值等信息补充...");
    $('#patchplusmes').val("")
    $('#silkprintplusmes').attr('placeholder', "颜色值等信息补充...");
    if ($('#watertranprint').is(":checked")) {
        $("#watertranprint").prop("checked", false);
    }
    $('#patchplusmes').val("")
    $('#watertranprintplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#patchplusmes').val("")
    $('#bakingpaintplusmes').attr('placeholder', "颜色值等信息补充...")
    $('#patchplusmes').val("")
    $('#hottransprintplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#patchplusmes').val("")
    $('#dustplusmessage').attr('placeholder', "颜色值等信息补充...");
    if ($('#bakingpaint').is(":checked")) {
        $("#bakingpaint").prop("checked", false);
    }
    if ($('#hottransprint').is(":checked")) {
        $("#hottransprint").prop("checked", false);
    }
    if ($('#dusting').is(":checked")) {
        $("#dusting").prop("checked", false);
    }

    if ($('#patch').is(":checked")) {
        $("#patch").prop("checked", false);
    }
    if ($('#elecplate').is(":checked")) {
        $("#elecplate").prop("checked", false);
    }
    if ($('#corrosion').is(":checked")) {
        $("#corrosion").prop("checked", false);
    }
    if ($('#waterplating').is(":checked")) {
        $("#waterplating").prop("checked", false);
    }

    $('#patchplusmes').val("")
    $('#patchplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#elecplateplusmes').val("")
    $('#elecplateplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#corrosionplusmes').val("")
    $('#corrosionplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#waterplatingplusmes').val("")
    $('#waterplatingplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#superglueplusmes').val("")
    $('#superglueplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#anodicoxidplusmes').val("")
    $('#anodicoxidplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#resinplusmes').val("")
    $('#resinplusmes').attr('placeholder', "颜色值等信息补充...");
    $('#paintfillplusmes').val("")
    $('#paintfillplusmes').attr('placeholder', "颜色值等信息补充...");

    if ($('#superglue').is(":checked")) {
        $("#superglue").prop("checked", false);
    }
    if ($('#anodicoxid').is(":checked")) {
        $("#anodicoxid").prop("checked", false);
    }
    if ($('#resin').is(":checked")) {
        $("#resin").prop("checked", false);
    }
    if ($('#paintfill').is(":checked")) {
        $("#paintfill").prop("checked", false);
    }
    if ($('#coverprotemethod').is(":checked")) {
        $("#coverprotemethod").prop("checked", false);
    }

    //$("#otherarts").val("其它工艺");
    $('#otherarts').attr('placeholder', "其它工艺...");

}


function submitmainmateriandart() {
    var maintotalremark = "";
    var errormessagediv = document.getElementById("errormessage1");
    errormessagediv.innerHTML = "";
    var alertmessage = "";
    var name = "";
    var stainlesteel = $("#stainlesteel").is(":checked");
    if (stainlesteel) {
        name = "不锈钢板,";
        var surattribute = $("#surattribute").val();
        if(surattribute==null) {
            alertmessage = "请选择不锈钢板的表面属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var materialattribute = $("#materialattribute").val();
        if(materialattribute==null) {
            alertmessage = "请选择不锈钢板的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }

        var thickness = $("#thickness").val();
        if(thickness==null) {
            alertmessage = "请选择不锈钢板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }

        var stainlesteelart = $("#stainlesteelart").val();
        maintotalremark += name + surattribute + "," + materialattribute + "," + thickness + "," + stainlesteelart + ";";
    }

    var stainless2 = $("#stainless2").is(":checked");
    if (stainless2) {
        name = "不锈钢板2,";
        var materialattribute = $("#coverattri2").val();
        if(materialattribute==null) {
            alertmessage = "请选择不锈钢板2的表面属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var thickness = $("#materialattri2").val();
        if(thickness==null) {
            alertmessage = "请选择不锈钢板2的表面属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var stainlesteelart = $("#thickness2").val();
        if(stainlesteelart==null) {
            alertmessage = "请选择不锈钢板2的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var stainless2art = $("#stainless2art").val();
        maintotalremark += name + materialattribute + "," + thickness + "," + stainlesteelart + "," + stainless2art + ";";
    }

    var aluminumalloyplate = $('#aluminumalloyplate').is(":checked");
    if (aluminumalloyplate) {
        name = "铝合金板,";
        var marialattr3 = $("#marialattr3").val();
        if(marialattr3==null) {
            alertmessage = "请选择铝合金板的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var thickness3 = $("#thickness3").val();
        if(thickness3==null) {
            alertmessage = "请选择铝合金板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var aluminumalloyplateart = $("#aluminumalloyplateart").val();
        maintotalremark += name + marialattr3 + "," + thickness3 + "," + aluminumalloyplateart + ";";
    }

    var ironplate = $('#ironplate').is(":checked");
    if (ironplate) {
        name = "铁板,";
        var materialattr5 = $("#materialattr5").val();
        if(materialattr5==null) {
            alertmessage = "请选择铁板的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var thickness5 = $("#thickness5").val();
        if(thickness5==null) {
            alertmessage = "请选择铁板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var ironplateart = $("#ironplateart").val();
        maintotalremark += name + materialattr5 + "," + thickness5 + "," + ironplateart + ";";
    }

    var copperplate = $('#copperplate').is(":checked");
    if (copperplate) {
        name = "铜板,";
        var materialattr15 = $("#materialattr15").val();
        if(materialattr15==null) {
            alertmessage = "请选择铜板的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var thickness6 = $("#thickness6").val();
        if(thickness6==null) {
            alertmessage = "请选择铜板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var copperplateart = $("#copperplateart").val();
        maintotalremark += name + materialattr15 + "," + thickness6 + "," + copperplateart + ";";
    }

    var galvanizedsheet = $('#galvanizedsheet').is(":checked");
    if (galvanizedsheet) {
        name = "镀锌板,";
        var thickness7 = $("#thickness7").val();
        if(thickness7==null) {
            alertmessage = "请选择镀锌板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var galvanizedsheetart = $("#galvanizedsheetart").val();
        maintotalremark += name + thickness7 + "," + galvanizedsheetart + ";";
    }

    var electplate = $("#electplate").is(":checked");
    if (electplate) {
        name = "电解板,";
        var thickness8 = $("#thickness8").val();
        if(thickness8==null) {
            alertmessage = "请选择电解板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var electplateart = $('#electplateart').val();
        maintotalremark += name + thickness8 + "," + electplateart + ";";
    }

    var aluminumplate = $("#aluminumplate").is(":checked");
    if (aluminumplate) {
        name = "铝板,";
        var thickness9 = $("#thickness9").val();
        if(thickness9==null) {
            alertmessage = "请选择铝板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var aluminumplateart = $('#aluminumplateart').val();
        maintotalremark += name + thickness9 + "," + aluminumplateart + ";";
    }


    var strake = $("#strake").is(":checked");
    if (strake) {
        name = "边条,";
        var materialattr6 = $("#materialattr6").val();
        if(materialattr6==null) {
            alertmessage = "请选择边条的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var strakeart = $('#strakeart').val();
        maintotalremark += name + materialattr6 + "," + strakeart + ";";
    }

    var acrylic = $('#acrylic').is(":checked");
    if (acrylic) {
        name = "亚克力板,";
        var thick01 = $("#thick01").val();
        if(thick01==null) {
            alertmessage = "请选择亚克力板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var colorattr = $("#colorattr").val();
        if(colorattr==null) {
            alertmessage = "请选择亚克力板的颜色属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var attrdescribtion = $("#attrdescribtion").val();
        var acrylicart = $('#acrylicart').val();
        maintotalremark += name + thick01 + "," + colorattr + "," + attrdescribtion + "," + acrylicart + ";";
    }

    var pcplate = $('#pcplate').is(":checked");
    if (pcplate) {
        name = "PC板,";
        var thickness02 = $("#thickness02").val();
        if(thickness02==null) {
            alertmessage = "请选择PC板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var colorattr02 = $("#colorattr02").val();
        if(colorattr02==null) {
            alertmessage = "请选择PC板的颜色属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var attrdescribtion02 = $("#attrdescribtion02").val();
        var pcplateart = $("#pcplateart").val();
        maintotalremark += name + thickness02 + "," + colorattr02 + "," + attrdescribtion02 + "," + pcplateart + ";";
    }

    var marblesalb = $('#marblesalb').is(":checked");
    if (marblesalb) {
        name = "仿大理石板,";
        var thickness03 = $("#thickness03").val();
        if(thickness03==null) {
            alertmessage = "请选择仿大理石板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var attrdiscribtion03 = $("#attrdiscribtion03").val();
        var marblesalbart = $("#marblesalbart").val();
        maintotalremark += name + thickness03 + "," + attrdiscribtion03 + "," + marblesalbart + ";";

    }
    var alucomppanel = $("#alucomppanel").is(":checked");
    if (alucomppanel) {
        name = "铝塑板,";
        var thickness05 = $("#thickness05").val();
        if(thickness05==null) {
            alertmessage = "请选择铝塑板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var colorattr05 = $("#colorattr05").val();
        if(colorattr05==null) {
            alertmessage = "请选择铝塑板的颜色属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var colordescribtion05 = $("#colordescribtion05").val();
        var alucomppanelart = $('#alucomppanelart').val();
        maintotalremark += name + thickness05 + "," + colorattr05 + "," + colordescribtion05 + "," + alucomppanelart + ";";
    }

    var pvcplate = $('#pvcplate').is(":checked");
    if (pvcplate) {
        name = "PVC板,";
        var thickness06 = $("#thickness06").val();
        if(thickness06==null) {
            alertmessage = "请选择PVC板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var colorattr06 = $("#colorattr06").val();
        if(colorattr06==null) {
            alertmessage = "请选择PVC板的颜色属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var attrdescribtion06 = $("#attrdescribtion06").val();
        var pvcplateart = $("#pvcplateart").val();
        maintotalremark += name + thickness06 + "," + colorattr06 + "," + attrdescribtion06 + "," + pvcplateart + ";";
    }

    var moodplate = $('#moodplate').is(":checked");
    if (moodplate) {
        name = "木板,";
        var thickness07 = $("#thickness07").val();
        if(thickness07==null) {
            alertmessage = "请选择木板的厚度!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var attrdescribtion07 = $("#attrdescribtion07").val();
        var moodplateart = $("#moodplateart").val();
        maintotalremark += name + thickness07 + "," + attrdescribtion07 + "," + moodplateart + ";";
    }

    var fangtong = $('#fangtong').is(":checked");
    if (fangtong) {
        name = "方通,";
        var materiattr = $("#materiattr").val();
        if(materiattr==null) {
            alertmessage = "请选择方通的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var addfangtongremark = $("#addfangtongremark").val();
        var fangtongart = $('#fangtongart').val();
        maintotalremark += name + materiattr + "," + addfangtongremark + "," + fangtongart + ";";
    }


    var biantong = $('#biantong').is(":checked");
    if (biantong) {
        name = "扁通,";
        var biantongthicness = $("#biantongthicness").val()
        if(biantongthicness==null) {
            alertmessage = "请选择扁通的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var addbiantongremark = $("#addbiantongremark").val();
        var biantongart = $('#biantongart').val();
        maintotalremark += name + biantongthicness + "," + addbiantongremark + "," + biantongart + ";";
    }


    var yuanguan = $('#yuanguan').is(":checked");
    if (yuanguan) {
        name = "圆管,";
        var yuanguanattri = $('#yuanguanattri').val();
        if(yuanguanattri==null) {
            alertmessage = "请选择圆管的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var addyuanguanremark = $("#addyuanguanremark").val();
        var yuanguanart = $('#yuanguanart').val();
        maintotalremark += name + yuanguanattri + "," + addyuanguanremark + "," + yuanguanart + ";";
    }

    var jiaotiao = $('#jiaotiao').is(":checked");
    if (jiaotiao) {
        name = "角条,";
        var jiaotiaoattri = $("#jiaotiaoattri").val();
        if(jiaotiaoattri==null) {
            alertmessage = "请选择角条的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var addjiaotiaoremark = $("#addjiaotiaoremark").val();
        var jiaotiaoart = $('#jiaotiaoart').val();
        maintotalremark += name + jiaotiaoattri + "," + addjiaotiaoremark + "," + jiaotiaoart + ";";
    }

    var yuanzhi = $('#yuanzhi').is(":checked");
    if (yuanzhi) {
        name = "圆支,";
        var yuanzhithicness = $("#yuanzhithicness").val();
        if(yuanzhithicness==null) {
            alertmessage = "请选择圆支的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var addyuanzhiremark = $("#addyuanzhiremark").val();
        var yuanzhiart = $('#yuanzhiart').val();
        maintotalremark += name + yuanzhithicness + "," + addyuanzhiremark + "," + yuanzhiart + ";";
    }


    var xingcai = $('#xingcai').is(":checked");
    if (xingcai) {
        name = "型材,";
        var xingcaithickness = $("#xingcaithickness").val();
        if(xingcaithickness==null) {
            alertmessage = "请选择型材的材质属性!";
            $("#errormessage1").show();
            errormessagediv.innerHTML = alertmessage;
            errormessagediv.style.color = "red";
            return;
        }
        var addxingcairemark = $("#addxingcairemark").val();
        var xingcaiart = $('#xingcaiart').val();
        maintotalremark += name + xingcaithickness + "," + addxingcairemark + "," + xingcaiart + ";";
    }

    var zikemateriremark = $("#zikemateriremark").val();
    if (zikemateriremark.trim().length > 0) {
        name = "字壳材质补充说明,";
        maintotalremark += name + zikemateriremark.trim()+";";
    }

    var covermateriremark = $("#covermateriremark").val();
    if (covermateriremark.trim().length > 0) {
        name = "面板材质补充说明,";
        maintotalremark += name + covermateriremark.trim()+";";
    }

    var plusremark = $("#plusremark").val();
    if (plusremark.trim().length > 0) {
        name = "附加工艺材质补充说明,";
        maintotalremark += name + plusremark.trim();
    }


    $("#maintotalremark").html(maintotalremark);
    var result = maintotalremark.replace(/<br>/g, '');
    $("#mainmateria").val(result);


    var popfive = document.getElementById("showmainmaterialandarts");
    document.getElementById("coverbehidepage").style.display = "none";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "none";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "none";
    document.getElementById("showmainmaterialandarts").style.display = "none";
    popfive.style.visibility = "hidden";

}

function confirmdatatoMysql() {
    var alertmessage = "";
    var errormessagediv = document.getElementById("errormessage");
    $("#errormessage").hide();
    var orderNo = $("#orderNo").val();
    if(orderNo.trim().length==0) {
        alertmessage = "订单号不能为空";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }
    var productName = $("#productName").val();
    if(productName.trim().length==0) {
        alertmessage = "品名不能为空";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }
    var deliverdate = $("#deliverdate").val();
    if(deliverdate.trim().length==0) {
        alertmessage = "交货时间不能为空";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var orderSetNum = $("#orderSetNum").val();
    if(orderSetNum.trim().length==0) {
        alertmessage = "需求数量不能为空";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var productBigType = $("#productBigType").val();
    if(productBigType.trim().length==0) {
        alertmessage = "请选择产品大类";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var productMainShape = $("#productMainShape").val();
    if(productMainShape.trim().length==0) {
        alertmessage = "请选择产品主体";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var newFinishProudNo = $("#newFinishProudNo").val();
    if(newFinishProudNo.trim().length==0) {
        alertmessage = "请输入新成品编号";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var productSize = $("#productSize").val();
    if(productSize.trim().length==0) {
        alertmessage = "请输入产品尺寸";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var edgeHightSize = $("#edgeHightSize").val();
    if(edgeHightSize.trim().length==0) {
        alertmessage = "请输入边高尺寸";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }


    var backInstallSelect = $("#backInstallSelect").val();
    if(backInstallSelect.trim().length==0) {
        alertmessage = "请选择背部安装选项";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }
    var mainMateriAndArt = $("#mainmateria").val();
    if(mainMateriAndArt.trim().length==0) {
        alertmessage = "主体材质需求及其工艺不能为空!";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var electMateriNeeds = $("#electmateri").val();
    if(electMateriNeeds.trim().length==0) {
        alertmessage = "电子类辅料需求不能为空!";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }

    var installTransfBacking = $("#installtransf").val();
    if(installTransfBacking.trim().length==0) {
        alertmessage = "安装/运输/包装不能为空!";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }
    document.getElementById("coverbehidepage").style.display = "block";
    document.forms[0].submit();

}

function opensmallartsfordealdiv(w, h) {
    var poptwo = document.getElementById("smallshowdivforartdeal");
    document.getElementById("coverbehidepagetwo").style.display = "block";
    document.getElementById("smallshowdivforartdeal").style.display = "block";
    poptwo.style.top = "100px";
    poptwo.style.left = "300px";
    poptwo.style.width = w + "px";
    poptwo.style.height = h + "px";
    poptwo.style.overflowY = "visible";
    poptwo.style.overflowX = "visible";
    poptwo.style.visibility = "visible";
}

function validateisnum() {
    $("#errormessage").hide();
    var errormessagediv = document.getElementById("errormessage");
    var reg = new RegExp("^[0-9]+(.[0-9]{1,3})?$");
    var edgeHightSize = $("#edgeHightSize").val();
    if (edgeHightSize.trim().length>0 && !reg.test(edgeHightSize.trim())) {
        $("#edgeHightSize").val("");
        var alertmessage = "边高尺寸只能为数字!";
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }
}

function validateisnum3(obj) {
    $("#errormessage").hide();
    var errormessagediv = document.getElementById("errormessage");
    var reg = new RegExp("^[0-9]+(.[0-9]{1,3})?$");
    var orderSetNum = $("#orderSetNum").val();
    if (orderSetNum.trim().length>0 && !reg.test(orderSetNum.trim())) {
        var alertmessage = "需求数量只能为数字!";
        $("#orderSetNum").val("")
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }
}

function validateisnum2() {
    $("#errormessage").hide();
    var errormessagediv = document.getElementById("errormessage");
    var reg = new RegExp("^[0-9]+(.[0-9]{1,3})?$");
    var productSize = $("#productSize").val();
    if (productSize.trim().length>0 && !reg.test(productSize.trim())) {
        var alertmessage = "产品尺寸只能为数字!";
        $("#productSize").val("");
        $("#errormessage").show();
        errormessagediv.innerHTML = alertmessage;
        errormessagediv.style.color = "red";
        return;
    }
}

function confirmtranorpackdiv() {
    var name = "";
    var maintotalremark = "";
    var alertmessage = "";
    var isFullMessage = true;

    var isoutorin = $("input[name='isoutorin']:checked").val();
    maintotalremark += "安装方式:" + isoutorin + ",";
    var needcheck = $("#needcheck").is(":checked");
    if (needcheck) {
        maintotalremark += $("#needcheck").val() + ",";
    }
    var needloushuikong = $("#needloushuikong").is(":checked");
    if (needloushuikong) {
        maintotalremark += $("#needloushuikong").val() + ",";
    }

    var isoutorin = $("input[name='anzhuangfangshi']:checked").val();
    if (isoutorin == "贴墙") {
        name = "贴墙:"
        var tieqiangtype = $("#tieqiangtype").val();
        if (tieqiangtype == null) {
            alertmessage = "贴墙类型";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }

        var tieqianganzhuatype = $("#tieqianganzhuatype").val();
        if (tieqianganzhuatype == null) {
            alertmessage = "贴墙安装方式";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }

        var tieqiangplusremark = $("#tieqiangplusremark").val();
        if (tieqiangplusremark.trim().length > 0) {
            maintotalremark += name + tieqiangtype + "," + tieqianganzhuatype + "," + tieqiangplusremark.trim() + ";";
        } else {
            maintotalremark += name + tieqiangtype + "," + tieqianganzhuatype + ";";
        }
    } else if (isoutorin == "离墙") {
        name = "离墙:"
        var liqianganzhuantype = $("#liqianganzhuantype").val();
        if (liqianganzhuantype == null) {
            alertmessage = "离墙安装方式";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }

        var liqiangplusremark = $("#liqiangplusremark").val();
        if (liqiangplusremark.trim().length > 0) {
            maintotalremark += name + liqianganzhuantype + "," + liqiangplusremark.trim() + ";";
        } else {
            maintotalremark += name + liqianganzhuantype + ";";
        }
    } else if (isoutorin == "吊装") {
        name = "吊装:"
        var diaozhuanganzhuatype = $("#diaozhuanganzhuatype").val();
        if (diaozhuanganzhuatype == null) {
            alertmessage = "吊装安装方式";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var diaozhuangremark = $("#diaozhuangremark").val();
        if (diaozhuangremark.trim().length > 0) {
            maintotalremark += name + diaozhuanganzhuatype + "," + diaozhuangremark.trim() + ";";
        } else {
            maintotalremark += name + diaozhuanganzhuatype + ";";
        }
    } else if (isoutorin == "cezhuang") {
        name = "侧装:"
        var cezhuangtype = $("#cezhuangtype").val();
        if (cezhuangtype == null) {
            alertmessage = "侧装安装方式";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var cezhuangplusremark = $("#cezhuangplusremark").val();
        if (cezhuangplusremark.trim().length > 0) {
            maintotalremark += name + cezhuangtype + "," + cezhuangplusremark.trim() + ";";
        } else {
            maintotalremark += name + cezhuangtype + ";";
        }
    } else if (isoutorin == "立装") {
        name = "立装:"
        var lizhuangtype = $("#lizhuangtype").val();
        if (lizhuangtype == null) {
            alertmessage = "立装安装方式";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var lizhuangplusremark = $("#lizhuangplusremark").val();
        if (lizhuangplusremark.trim().length > 0) {
            maintotalremark += name + lizhuangtype + "," + lizhuangplusremark.trim() + ";";
        } else {
            maintotalremark += name + lizhuangtype + ";";
        }
    } else if (isoutorin == "盒装") {
        name = "盒装:"
        var hezhuangtype = $("#hezhuangtype").val();
        if (hezhuangtype == null) {
            alertmessage = "盒装安装方式";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var hezhuangplusremark = $("#hezhuangplusremark").val();
        if (hezhuangplusremark.trim().length > 0) {
            maintotalremark += name + hezhuangtype + "," + hezhuangplusremark.trim() + ";";
        } else {
            maintotalremark += name + hezhuangtype + ";";
        }
    } else if (isoutorin == "其它") {
        name = "其它";
        var otheranzhuangplusremark = $("#otheranzhuangplusremark").val();
        if (otheranzhuangplusremark.trim().length > 0) {
            maintotalremark += name + otheranzhuangplusremark.trim() + ";";
        } else {
            alertmessage = "其它安装方式";
            alert(alertmessage + "不能为空!");
            isFullMessage = false;
            return;
        }
    }
    var standarddrawing = $("#standarddrawing").is(":checked");
    if (standarddrawing) {
        maintotalremark += $("#standarddrawing").val() + ",";
    }

    var plusjiexiantu = $("#plusjiexiantu").is(":checked");
    if (plusjiexiantu) {
        maintotalremark += $("#plusjiexiantu").val() + ",";
    }

    var anzhuangotherremark = $("#anzhuangotherremark").val().trim();
    if(anzhuangotherremark.length>0) {
        maintotalremark += anzhuangotherremark + ",";
    }


    var transftertype = $("#transftertype").val();
    if (transftertype == null) {
        alertmessage = "运输方式";
        alert("请选择" + alertmessage);
        isFullMessage = false;
        return;
    }
    maintotalremark += "<br>运输方式:" + transftertype + ",";

    var needtag = $("#needtag").is(":checked");
    if (needtag) {
        maintotalremark += $("#needtag").val() + ",";
    }

    var transfotherremark = $("#transfotherremark").val();
    if (transfotherremark.trim().length > 0) {
        maintotalremark += transfotherremark.trim() + ";";
    }


    var packingtype = $("#packingtype").val();
    if (packingtype == null) {
        alertmessage = "包装方式";
        alert("请选择" + alertmessage);
        isFullMessage = false;
        return;
    }
    maintotalremark += "<br>包装方式:" + packingtype + ",";

    var packingotherremark = $("#packingotherremark").val();
    if (packingotherremark.trim().length > 0) {
        maintotalremark += packingotherremark.trim() + ";";
    }

    $("#anzhuangtransfbackingremark").html(maintotalremark);
    var result = maintotalremark.replace(/<br>/g, '');
    $("#installtransf").val(result);
    var popfive = document.getElementById("safttransandpacking");
    document.getElementById("coverbehidepage").style.display = "none";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "none";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "none";
    document.getElementById("safttransandpacking").style.display = "none";
    popfive.style.visibility = "hidden";
}

function shutdownpackingdiv() {
    var popfive = document.getElementById("safttransandpacking");
    document.getElementById("coverbehidepage").style.display = "none";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "none";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "none";
    document.getElementById("safttransandpacking").style.display = "none";
    popfive.style.visibility = "hidden";
}

function opensafttransandpackingdiv(w, h) {
    var poptwo = document.getElementById("safttransandpacking");
    document.getElementById("coverbehidepage").style.display = "block";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "block";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "block";
    document.getElementById("safttransandpacking").style.display = "block";
    poptwo.style.top = "20px";
    poptwo.style.left = "250px";
    poptwo.style.width = w + "px";
    poptwo.style.height = h + "px";
    poptwo.style.overflowY = "visible";
    poptwo.style.overflowX = "visible";
    poptwo.style.visibility = "visible";

}

function confirmelectmaterialneeds() {
    var name = "";
    var maintotalremark = "";
    var alertmessage = "";
    var isFullMessage = true;

    var dianyuan = $("#dianyuan").is(":checked");
    if (dianyuan) {
        name = "电源:";
        var dianyuanrenzhen = $("#dianyuanrenzhen").val();
        if (dianyuanrenzhen == null) {
            alertmessage = "电源认证";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dianyuanfangshui = $("#dianyuanfangshui").val();
        if (dianyuanfangshui == null) {
            alertmessage = "电源防水";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }

        var dianyuanplusremark = $("#dianyuanplusremark").val();
        if (dianyuanplusremark.length > 0) {
            maintotalremark += name + dianyuanrenzhen + "," + dianyuanfangshui + "," + dianyuanplusremark + ","+$("#dianyuanremark").val()+";";
        } else {
            maintotalremark += name + dianyuanrenzhen + "," + dianyuanfangshui + ","+$("#dianyuanremark").val()+";";
        }

    }

    var dianxian = $("#dianxian").is(":checked");
    if (dianxian) {
        name = "电线:";
        var dianxianrenzheng = $("#dianxianrenzheng").val();
        if (dianxianrenzheng == null) {
            alertmessage = "电线认证";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dianxianbiaozhun = $("#dianxianbiaozhun").val();
        if (dianxianbiaozhun == null) {
            alertmessage = "电线标准";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dianxianplusremark = $("#dianxianplusremark").val();
        if (dianxianplusremark.length > 0) {
            maintotalremark += name + dianxianrenzheng + "," + dianxianbiaozhun + "," + dianxianplusremark + ","+$("#dianyuanremark").val()+";";
        } else {
            maintotalremark += name + dianxianrenzheng + "," + dianxianbiaozhun + ","+$("#dianyuanremark").val()+";";
        }

    }

    var mozu = $("#mozu").is(":checked");
    if (mozu) {
        name = "模组:";
        var mozurenzheng = $("#mozurenzheng").val();
        if (mozurenzheng == null) {
            alertmessage = "模组认证";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var mozufuangshui = $("#mozufuangshui").val();
        if (mozufuangshui == null) {
            alertmessage = "模组防水";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var mozecolor = $("#mozecolor").val();
        if (mozecolor == null) {
            alertmessage = "模组颜色";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var mozuplusremark = $("#mozuplusremark").val();
        if (mozuplusremark.length > 0) {
            maintotalremark += name + mozurenzheng + "," + mozufuangshui + "," + mozecolor + "," + mozuplusremark + ","+$("#dianyuanremark").val()+";";
        } else {
            maintotalremark += name + mozurenzheng + "," + mozufuangshui + "," + mozecolor + ","+$("#dianyuanremark").val()+";";
        }
    }

    var dendai = $("#dendai").is(":checked");
    if (dendai) {
        name = "灯带:";
        var dendairenzheng = $("#dendairenzheng").val();
        if (dendairenzheng == null) {
            alertmessage = "灯带认证";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dendaifangshui = $("#dendaifangshui").val();
        if (dendaifangshui == null) {
            alertmessage = "灯带防水";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dendaicolor = $("#dendaicolor").val();
        if (dendaicolor == null) {
            alertmessage = "灯带颜色";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dendaiplusremark = $("#dendaiplusremark").val();
        if (dendaiplusremark.length > 0) {
            maintotalremark += name + dendairenzheng + "," + dendaifangshui + "," + dendaicolor + "," + dendaiplusremark + ","+$("#dianyuanremark").val()+";";
        } else {
            maintotalremark += name + dendairenzheng + "," + dendaifangshui + "," + dendaicolor + ","+$("#dianyuanremark").val()+";";
        }
    }

    var zhengzhu = $("#zhengzhu").is(":checked");
    if (zhengzhu) {
        name = "灯珠:";
        var zhengzhurenzheng = $("#zhengzhurenzheng").val();
        if (zhengzhurenzheng == null) {
            alertmessage = "灯珠认证";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var zhengzhufangshui = $("#zhengzhufangshui").val();
        if (zhengzhufangshui == null) {
            alertmessage = "灯珠防水";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dengzhucolor = $("#dengzhucolor").val();
        if (dengzhucolor == null) {
            alertmessage = "灯珠颜色";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dengzhuplusremark = $("#dengzhuplusremark").val();
        if (dengzhuplusremark > 0) {
            maintotalremark += name + zhengzhurenzheng + "," + zhengzhufangshui + "," + dengzhucolor + "," + dengzhuplusremark + ","+$("#dianyuanremark").val()+";";
        } else {
            maintotalremark += name + zhengzhurenzheng + "," + zhengzhufangshui + "," + dengzhucolor + ","+$("#dianyuanremark").val()+";";
        }
    }

    var dengtiao = $("#dengtiao").is(":checked");
    if (dengtiao) {
        name = "灯条:";
        var dengtiaorenzheng = $("#dengtiaorenzheng").val();
        if (dengtiaorenzheng == null) {
            alertmessage = "灯条认证";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dengtiaofangshui = $("#dengtiaofangshui").val();
        if (dengtiaofangshui == null) {
            alertmessage = "灯条防水";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }
        var dengtiaocolor = $("#dengtiaocolor").val();
        if (dengtiaocolor == null) {
            alertmessage = "灯条颜色";
            alert("请选择" + alertmessage);
            isFullMessage = false;
            return;
        }

        var dengtiaoplusremark = $("#dengtiaoplusremark").val();
        if (dengtiaoplusremark.length > 0) {
            maintotalremark += name + dengtiaorenzheng + "," + dengtiaofangshui + "," + dengtiaocolor + "," + dengtiaoplusremark + ","+$("#dianyuanremark").val()+";";
        } else {
            maintotalremark += name + dengtiaorenzheng + "," + dengtiaofangshui + "," + dengtiaocolor + "," +$("#dianyuanremark").val()+";";
        }
    }

    $("#electmateremark").html(maintotalremark);
    var result = maintotalremark.replace(/<br>/g, '');
    $("#electmateri").val(result);
    var popfive = document.getElementById("showelectmaterialneeds");
    document.getElementById("coverbehidepage").style.display = "none";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "none";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "none";
    document.getElementById("showelectmaterialneeds").style.display = "none";
    popfive.style.visibility = "hidden";

}

function shudownelectmaterialneedsdiv() {
    var popfive = document.getElementById("showelectmaterialneeds");
    document.getElementById("coverbehidepage").style.display = "none";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "none";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "none";
    document.getElementById("showelectmaterialneeds").style.display = "none";
    popfive.style.visibility = "hidden";
}


function shutdownbigdiv() {
    var popfive = document.getElementById("showmainmaterialandarts");
    document.getElementById("coverbehidepage").style.display = "none";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "none";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "none";
    document.getElementById("showmainmaterialandarts").style.display = "none";
    popfive.style.visibility = "hidden";
}

function showelectmaterialneeds(w, h) {
    var poptwo = document.getElementById("showelectmaterialneeds");
    document.getElementById("coverbehidepage").style.display = "block";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "block";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "block";
    document.getElementById("showelectmaterialneeds").style.display = "block";
    poptwo.style.top = "80px";
    poptwo.style.left = "250px";
    poptwo.style.width = w + "px";
    poptwo.style.height = h + "px";
    poptwo.style.overflowY = "visible";
    poptwo.style.overflowX = "visible";
    poptwo.style.visibility = "visible";
}

function showmainmaterialandarts(w, h) {
    var popUp = document.getElementById("showmainmaterialandarts");
    document.getElementById("coverbehidepage").style.display = "block";
    window.parent.document.getElementById('coverbehidepageLeft').style.display = "block";
    window.parent.document.getElementById('coverbehidepageTop').style.display = "block";
    document.getElementById("showmainmaterialandarts").style.display = "block";
    popUp.style.top = "0px";
    popUp.style.left = "65px";
    popUp.style.width = w + "px";
    popUp.style.height = h + "px";
    popUp.style.overflowY = "visible";
    popUp.style.overflowX = "visible";
//if (baseText == null) baseText = popUp.innerHTML;
//popUp.innerHTML = baseText + "<div id=\"statusbar\"><button onclick=\"hidePopup();\">Close window</button></div>";
//var sbar = document.getElementById("statusbar");
//sbar.style.marginTop = (parseInt(h)-40) + "px";
    popUp.style.visibility = "visible";
}