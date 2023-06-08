package IPVC.Controller;

import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.LinhaReciboBLL;
import IPVC.BLL.ProdutoBLL;
import IPVC.BLL.ReciboBLL;
import IPVC.DAL.Entidade;
import IPVC.DAL.LinhaRecibo;
import IPVC.DAL.Produto;
import IPVC.DAL.Recibo;
import IPVC.models.FullReciboModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class userOrdersController {

    @GetMapping("/orders")
    public String userOrders(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nomeUtilizador = (String) session.getAttribute("nomeUtilizador");
        Entidade user = EntidadeBLL.getEntityByName(nomeUtilizador);

        ArrayList<FullReciboModel> recibos = new ArrayList<>();

        for(Recibo r : ReciboBLL.getByUser(user)){
            FullReciboModel recibo = new FullReciboModel();
            recibo.setRecibo(r);
            recibo.setLinhasRecibo(LinhaReciboBLL.getProductsByRecibo(r));
            recibos.add(recibo);
        }


        model.addAttribute("nomeUtilizador", nomeUtilizador);
        model.addAttribute("recibos", recibos);

        return "userOrders";
    }

}
