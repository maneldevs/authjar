@import es.maneldevs.domain.model.App
@import java.util.List

@param String userEmail
@param List<App> apps

@template.layout.main(title = "Apps", userEmail = userEmail, activeItem = "apps", content = @`
    <div class="d-flex justify-content-end mb-3">
        <a href="/web/apps/new" class="btn btn-outline-success" data-bs-toggle="tooltip" data-bs-placement="left"
            title="Create new app" aria-label="Create new app">
            <i class="bi bi-plus-lg" aria-hidden="true"></i>
            New app
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
            @for(App app : apps)
            <tr>
                <td>${app.getName()}</td>
                <td class="text-end">
                    <a href="/web/apps/${app.getId()}/edit" class="btn btn-outline-secondary btn-sm me-1"
                        data-bs-toggle="tooltip" title="Edit app" aria-label="Edit app">
                        <i class="bi bi-pencil" aria-hidden="true"></i>
                    </a>
                </td>
            </tr>
            @endfor
        </tbody>
    </table>

    @if(apps.isEmpty())
    <p class="text-muted">No apps registered.</p>
    @endif
`)
