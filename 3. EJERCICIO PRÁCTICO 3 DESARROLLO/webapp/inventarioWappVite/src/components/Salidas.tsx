import { useEffect, useState } from 'react';
import ReactPaginate from 'react-paginate';
import { Card, Button, Row, Col, Form } from 'react-bootstrap';
import { postData, putData } from '../services/apiService';

const Salidas = ({ user }) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [productList, setProductList] = useState([]);
  const [itemsPerPage, setItemsPerPage] = useState(5); 
  const [filtro, setFiltro] = useState({
    maxRegPagina: 5,
    total: 0,
    next: 0,
    offSet: 0,
    paginas: 0,
    datos: {
      id: 0,
      ubicacion: "",
      status: 1,
      producto: {
        id: 0
      }
    }
  });
  const [quantities, setQuantities] = useState({});

  const hasPermission = (clavePermiso) => {
    if (user && user.rol && user.rol.permisos && user.rol.permisos.find((p) => p.permiso.clave === clavePermiso)) {
      return true;
    }
    return false;
  };

  const loadInventario = async () => {
    try {
      const updatedFiltro = {
        ...filtro,
        offSet: currentPage * itemsPerPage,
        maxRegPagina: itemsPerPage,
        next: itemsPerPage
      };
      const response = await postData('/inventarios/obtener', updatedFiltro);
      setProductList(response);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const loadFiltro = async () => {
    try {
      const updatedFiltro = {
        ...filtro,
        offSet: currentPage * itemsPerPage,
        maxRegPagina: itemsPerPage,
        next: itemsPerPage
      };
      const response = await postData('/inventarios/obtenerFiltro', updatedFiltro);
      setFiltro(response);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  useEffect(() => {
    loadFiltro();
  }, []);

  useEffect(() => {
    loadInventario();
  }, [currentPage, itemsPerPage]);

  // Calculamos los productos actuales que se deben mostrar
  const offset = currentPage * itemsPerPage;
  const currentItems = productList.slice(offset, offset + itemsPerPage);

  // Manejar el cambio de página
  const handlePageClick = (event) => {
    setCurrentPage(event.selected);
  };

  // Manejar el cambio de cantidad
  const handleQuantityChange = (id, quantity) => {
    setQuantities({
      ...quantities,
      [id]: quantity,
    });
  };

  // Manejar la salida de productos
  const handleSalida = async (id) => {
    const quantity = quantities[id] || 0;
    try {
      let product = productList.find(product => product.id === id);
      const updatedProduct = { ...product, stock: quantity };
      await putData(`/inventarios/actualizar/${user.id}/${id}`, updatedProduct);
      product.stock += quantity;
      setProductList(productList.map(product => 
        product
      ));
      setQuantities({
        ...quantities,
        [id]: 0,
      });
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div>
      <h2 className="my-4 text-center">Salida de Inventario</h2>

      <Row xs={1} md={2} lg={3} className="g-4">
        {currentItems.map((product) => (
          <Col key={product.id}>
            <Card>
              <Card.Img variant="top" src={product.producto.urlImagen} alt={product.producto.nombre} />
              <Card.Body>
                <Card.Title>{product.producto.nombre}</Card.Title>
                <Card.Text>{product.producto.descripcion}</Card.Text>
                <Card.Text>
                  <strong>Precio: ${product.producto.precio}</strong>
                </Card.Text>
                <Card.Text>
                  <strong>Stock: {product.stock}</strong>
                </Card.Text>
                <Form.Group controlId={`quantity-${product.id}`}>
                  <Form.Label>Cantidad a dar salida</Form.Label>
                  <Form.Control
                    type="number"
                    value={quantities[product.id] || ''}
                    onChange={(e) => {
                      const value = parseInt(e.target.value);
                      if (value <= 0) {
                        handleQuantityChange(product.id, value);
                      }
                    }}
                    disabled={!hasPermission("SAL02")}
                  />
                </Form.Group>
                <Button variant="primary" onClick={() => handleSalida(product.id)} disabled={!hasPermission("SAL02")}>
                  Dar Salida
                </Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      <div className="d-flex justify-content-center mt-4">
        <ReactPaginate
          previousLabel={'Anterior'}
          nextLabel={'Siguiente'}
          breakLabel={'...'}
          pageCount={Math.ceil(productList.length / itemsPerPage)} // Calcular el número total de páginas
          marginPagesDisplayed={2}
          pageRangeDisplayed={5}
          onPageChange={handlePageClick}
          containerClassName={'pagination'}
          activeClassName={'active'}
          previousClassName={'page-item'}
          nextClassName={'page-item'}
          pageClassName={'page-item'}
          disabledClassName={'disabled'}
        />
      </div>
    </div>
  );
};

export default Salidas;