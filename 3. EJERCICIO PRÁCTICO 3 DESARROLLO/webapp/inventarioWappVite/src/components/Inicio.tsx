import imgInventory from '../assets/inventory.svg';
import imgOut from '../assets/out.svg';
import imgTime from '../assets/time.svg';


const Inicio = ({user}) => {

  const menu = [];

  user.rol.permisos.forEach(permiso => {  
    if(permiso.permiso.clave === "INV01"){
      menu.push({Descripcion: "Inventario", Clave: "INV01", Icon: imgInventory, Url: "/Inventario"});
    }
    if(permiso.permiso.clave === "SAL01"){
      menu.push({Descripcion: "Salida de productos", Clave: "SAL01", Url: "/Salidas", Icon: imgOut});
    }
    if(permiso.permiso.clave === "HIS01"){
      menu.push({Descripcion: "Histórico", Clave: "HIS01", Url: "/Historico", Icon: imgTime});
    }
  });

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="row row-cols-1 row-cols-md-2">
          {
            menu.map(({Descripcion, Clave, Url, Icon})=>(
                <div className="col">
                    <a href={Url}>
                        <div className="card shadow-lg text-center h-100" key={Clave}>
                            <img src={Icon} className="card-img-top" alt={Clave}></img>
                            <div className="card-body">
                                <h5 className="card-title">{Descripcion}</h5>
                            </div>
                        </div>
                    </a>
                </div>
            ))
          }
      </div>
    </div>
  );
};

export default Inicio;

