@import es.maneldevs.domain.model.ClientApp
@import es.maneldevs.domain.model.Client
@import java.util.List

@param String userEmail
@param Client client
@param List<ClientApp> clientApps

@template.layout.main(title = "Client apps", userEmail = userEmail, activeItem = "clients", content = @`
    <div class="d-flex justify-content-between align-items-center mb-3">
        <a href="/web/clients" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left" aria-hidden="true"></i>
            Back to clients
        </a>
        <a href="/web/clients/${client.getId()}/apps/new" class="btn btn-outline-success" data-bs-toggle="tooltip" data-bs-placement="left"
            title="Create new client app" aria-label="Create new client app">
            <i class="bi bi-plus-lg" aria-hidden="true"></i>
            New app instance
        </a>
    </div>

    <table class="table table-hover align-middle">
        <thead>
            <tr>
                <th>Name</th>
                <th>Client</th>
                <th>App</th>
                <th>Status</th>
                <th class="text-end">Actions</th>
            </tr>
        </thead>
        <tbody>
            @for(ClientApp clientApp : clientApps)
            <tr>
                <td>${clientApp.getName()}</td>
                <td>${clientApp.getClient().getName()}</td>
                <td>${clientApp.getApp().getName()}</td>
                <td>
                    @if(clientApp.getActive())
                    <span class="badge bg-success">Active</span>
                    @else
                    <span class="badge bg-danger">Inactive</span>
                    @endif
                </td>
                <td class="text-end">
                    <a href="/web/clients/${client.getId()}/apps/${clientApp.getApp().getId()}/edit" class="btn btn-outline-secondary btn-sm me-1"
                        data-bs-toggle="tooltip" title="Edit app instance" aria-label="Edit app instance">
                        <i class="bi bi-pencil" aria-hidden="true"></i>
                    </a>
                </td>
            </tr>
            @endfor
        </tbody>
    </table>

    @if(clientApps.isEmpty())
    <p class="text-muted">No client apps registered.</p>
    @endif
`)
