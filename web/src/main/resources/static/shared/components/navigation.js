class AppHeader extends HTMLElement {
    connectedCallback() {
      this.innerHTML = `
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top shadow-sm" id="mainNav">
            <div class="container px-5">
                <a class="navbar-brand fw-bold" href="#page-top">
                    <img src="/src/public/assets/img/taxi-front-fill.svg" alt="..." style="height: 1.5rem" />
                    Stin City Taxi    
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="bi-list"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ms-auto me-4 my-3 my-lg-0">
                        <li class="nav-item"><a class="nav-link me-lg-3" href="#features">Historial de viajes</a></li>
                        <!--<li class="nav-item"><a class="nav-link me-lg-3" href="#download"><Salir</a></li>-->
                    </ul>
                    <button class="btn btn-primary rounded-pill px-3 mb-2 mb-lg-0" data-bs-toggle="modal" data-bs-target="#feedbackModal">
                        <span class="d-flex align-items-center">
                            <i class="bi bi-person me-2"></i>
                            <span class="small">Cuenta</span>
                        </span>
                    </button>
                </div>
            </div>
        </nav>
      `;
    }
  }

  customElements.define("app-header", AppHeader);