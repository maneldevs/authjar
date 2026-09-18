@import es.maneldevs.domain.model.Client
@import java.util.List

@param String userEmail
@param List<Client> clients

@template.layout.main(title = "Clientes", userEmail = userEmail, activeItem = "clients", content = @`
    <div class="d-flex justify-content-end mb-3">
        <a href="/web/clients/new" class="btn btn-outline-success" data-bs-toggle="tooltip" data-bs-placement="left"
            title="Create new client" aria-label="Create new client">
            <i class="bi bi-plus-lg" aria-hidden="true"></i>
            New client
        </a>
    </div>

    <table class="table table-hover align-middle">
        <thead>
            <tr>
                <th>Name</th>
                <th class="text-end">Actions</th>
            </tr>
        </thead>
        <tbody>
            @for(Client client : clients)
            <tr>
                <td>${client.getName()}</td>
                <td class="text-end">
                    <a href="/web/clients/${client.getId()}/edit" class="btn btn-outline-secondary btn-sm me-1"
                        data-bs-toggle="tooltip" title="Edit client" aria-label="Edit client">
                        <i class="bi bi-pencil" aria-hidden="true"></i>
                    </a>
                    <a href="/web/clients/${client.getId()}/apps" class="btn btn-outline-secondary btn-sm me-1"
                        data-bs-toggle="tooltip" title="View applications" aria-label="View applications">
                        <i class="bi bi-grid" aria-hidden="true"></i>
                    </a>
                </td>
            </tr>
            @endfor
        </tbody>
    </table>

    @if(clients.isEmpty())
    <p class="text-muted">No clients registered.</p>
    @endif
`)
